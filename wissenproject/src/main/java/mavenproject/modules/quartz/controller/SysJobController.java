package mavenproject.modules.quartz.controller;

import mavenproject.common.security.util.SecurityUtils;
import mavenproject.common.utils.R;
import mavenproject.modules.quartz.entity.SysJob;
import mavenproject.modules.quartz.entity.SysJobLog;
import mavenproject.modules.quartz.service.SysJobLogService;
import mavenproject.modules.quartz.service.SysJobService;
import mavenproject.modules.quartz.util.TaskUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.quartz.Scheduler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static mavenproject.modules.quartz.constant.enums.QuartzEnum.*;


/**
 * @author jjh
 * 定时任务管理
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sys-job")
public class SysJobController {
    private final SysJobService sysJobService;
    private final SysJobLogService sysJobLogService;
    private final TaskUtil taskUtil;
    private final Scheduler scheduler;

    /**
     * 定时任务分页查询
     *
     * @param page   分页对象
     * @param sysJob 定时任务调度表
     * @return
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('job:view')")
    public R getSysJobPage(Page page, SysJob sysJob) {
        return R.ok(sysJobService.page(page, Wrappers.lambdaQuery(sysJob)));
    }


    /**
     * 通过id查询定时任务
     *
     * @param id id
     * @return R
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('job:view')")
    public R getById(@PathVariable("id") Integer id) {
        return R.ok(sysJobService.getById(id));
    }

    /**
     * 新增定时任务
     *
     * @param sysJob 定时任务调度表
     * @return R
     */
    @PostMapping
    @PreAuthorize("hasAuthority('job:add')")
    @Transactional(rollbackFor = Exception.class)
    public R save(@RequestBody SysJob sysJob) {
        sysJob.setJobStatus(JOB_STATUS_RELEASE.getType());
        sysJob.setCreateTime(LocalDateTime.now());
        sysJob.setUpdateTime(LocalDateTime.now());
        boolean save = sysJobService.save(sysJob);
        return R.ok("添加成功");
    }

    /**
     * 修改定时任务
     *
     * @param sysJob 定时任务调度表
     * @return R
     */
    @PutMapping
    @PreAuthorize("hasAuthority('job:edit')")
    @Transactional(rollbackFor = Exception.class)
    public R updateById(@RequestBody SysJob sysJob) {
        sysJob.setUpdateBy(SecurityUtils.getUser().getId());
        sysJob.setUpdateTime(LocalDateTime.now());
        SysJob querySysJob = this.sysJobService.getById(sysJob.getJobId());
        sysJob.setJobStatus(querySysJob.getJobStatus());
        if (JOB_STATUS_NOT_RUNNING.getType().equals(querySysJob.getJobStatus())) {
            this.taskUtil.addOrUpateJob(sysJob, scheduler);
            sysJobService.updateById(sysJob);
        } else if (JOB_STATUS_RELEASE.getType().equals(querySysJob.getJobStatus())) {
            sysJobService.updateById(sysJob);
        }
        return R.ok("修改成功");
    }

    /**
     * 通过id删除定时任务
     *
     * @param id id
     * @return R
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('job:del')")
    @Transactional(rollbackFor = Exception.class)
    public R removeById(@PathVariable Integer id) {
        SysJob querySysJob = this.sysJobService.getById(id);
        if (JOB_STATUS_NOT_RUNNING.getType().equals(querySysJob.getJobStatus())) {
            this.taskUtil.removeJob(querySysJob, scheduler);
            this.sysJobService.removeById(id);
        } else if (JOB_STATUS_RELEASE.getType().equals(querySysJob.getJobStatus())) {
            this.sysJobService.removeById(id);
        } else {
            return R.failed("暂停之后才能删除");
        }
        return R.ok("删除成功");
    }

    /**
     * 暂停全部定时任务
     *
     * @return
     */
    @PostMapping("/shutdown-jobs")
    @PreAuthorize("hasAuthority('job:edit')")
    @Transactional(rollbackFor = Exception.class)
    public R shutdownJobs() {
        taskUtil.pauseJobs(scheduler);
        int count = this.sysJobService.count(new LambdaQueryWrapper<SysJob>()
                .eq(SysJob::getJobStatus, JOB_STATUS_RUNNING.getType()));
        if (count <= 0) {
            return R.ok("无正在运行定时任务");
        } else {
            //更新定时任务状态条件，运行状态2更新为暂停状态2
            this.sysJobService.update(SysJob.builder()
                    .jobStatus(JOB_STATUS_NOT_RUNNING.getType()).build(), new UpdateWrapper<SysJob>()
                    .lambda().eq(SysJob::getJobStatus, JOB_STATUS_RUNNING.getType()));
            return R.ok("暂停成功");
        }
    }

    /**
     * 启动全部定时任务
     *
     * @return
     */
    @PostMapping("/start-jobs")
    @PreAuthorize("hasAuthority('job:edit')")
    @Transactional(rollbackFor = Exception.class)
    public R startJobs() {
        //更新定时任务状态条件，暂停状态3更新为运行状态2
        this.sysJobService.update(SysJob.builder().jobStatus(JOB_STATUS_RUNNING
                .getType()).build(), new UpdateWrapper<SysJob>().lambda()
                .eq(SysJob::getJobStatus, JOB_STATUS_NOT_RUNNING.getType()));
        taskUtil.startJobs(scheduler);
        return R.ok();
    }

    /**
     * 刷新全部定时任务
     *
     * @return
     */
    @PostMapping("/refresh-jobs")
    @Transactional(rollbackFor = Exception.class)
    public R refreshJobs() {
        sysJobService.list().forEach((sysjob) -> {
            if (JOB_STATUS_RELEASE.getType().equals(sysjob.getJobStatus())
                    || JOB_STATUS_DEL.getType().equals(sysjob.getJobStatus())) {
                taskUtil.removeJob(sysjob, scheduler);
            } else if (JOB_STATUS_RUNNING.getType().equals(sysjob.getJobStatus())
                    || JOB_STATUS_NOT_RUNNING.getType().equals(sysjob.getJobStatus())) {
                taskUtil.addOrUpateJob(sysjob, scheduler);
            } else {
                taskUtil.removeJob(sysjob, scheduler);
            }
        });
        return R.ok();
    }

    /**
     * 启动定时任务
     *
     * @param jobId
     * @return
     */
    @PostMapping("/start-job/{id}")
    @PreAuthorize("hasAuthority('job:edit')")
    @Transactional(rollbackFor = Exception.class)
    public R startJob(@PathVariable("id") Integer jobId) {
        SysJob querySysJob = this.sysJobService.getById(jobId);
        if (querySysJob != null && JOB_LOG_STATUS_FAIL.getType()
                .equals(querySysJob.getJobStatus())) {
            taskUtil.addOrUpateJob(querySysJob, scheduler);
        } else {
            taskUtil.resumeJob(querySysJob, scheduler);
        }
        //更新定时任务状态条件，暂停状态3更新为运行状态2
        this.sysJobService.updateById(SysJob.builder().jobId(jobId)
                .jobStatus(JOB_STATUS_RUNNING.getType()).build());
        return R.ok();
    }

    /**
     * 暂停定时任务
     *
     * @return
     */
    @PostMapping("/shutdown-job/{id}")
    @PreAuthorize("hasAuthority('job:edit')")
    @Transactional(rollbackFor = Exception.class)
    public R shutdownJob(@PathVariable("id") Integer id) {
        SysJob querySysJob = this.sysJobService.getById(id);
        //更新定时任务状态条件，运行状态2更新为暂停状态3
        this.sysJobService.updateById(SysJob.builder().jobId(querySysJob.getJobId())
                .jobStatus(JOB_STATUS_NOT_RUNNING.getType()).build());
        taskUtil.pauseJob(querySysJob, scheduler);
        return R.ok();
    }

    /**
     * 唯一标识查询定时执行日志
     *
     * @return
     */
    @GetMapping("/job-log")
    public R getJobLog(Page page, SysJobLog sysJobLog) {
        return R.ok(sysJobLogService.page(page, Wrappers.query(sysJobLog)));
    }

    /**
     * 检验任务名称和任务组联合是否唯一
     *
     * @return
     */
    @GetMapping("/is-valid-task-name")
    public R isValidTaskName(@RequestParam String jobName, @RequestParam String jobGroup) {
        return this.sysJobService
                .count(Wrappers.query(SysJob.builder().jobName(jobName).jobGroup(jobGroup).build())) > 0
                ? R.failed() : R.ok();
    }

    /**
     * 启动定时任务
     *
     * @param jobId
     * @return
     */
    @PostMapping("/run-job/{id}")
    @PreAuthorize("hasAuthority('job:edit')")
    public R runJob(@PathVariable("id") Integer jobId) {
        SysJob querySysJob = this.sysJobService.getById(jobId);
        if (querySysJob.getJobStatus().equals(JOB_STATUS_RUNNING.getType())) {
            return R.failed("请先暂停在执行");
        }
        return TaskUtil.runOnce(scheduler, querySysJob) ? R.ok("执行成功") : R.failed("执行失败");
    }
}
