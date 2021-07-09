package mavenproject.modules.quartz.util;


import cn.hutool.core.util.StrUtil;
import mavenproject.modules.quartz.constant.enums.QuartzEnum;
import mavenproject.modules.quartz.entity.SysJob;
import mavenproject.modules.quartz.entity.SysJobLog;
import mavenproject.modules.quartz.event.SysJobLogEvent;
import mavenproject.modules.quartz.service.SysJobService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.quartz.CronTrigger;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import java.time.ZoneId;
import java.util.Date;

/**
 * 定时任务反射工具类
 *
 * @author
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class TaskInvokUtil {
    private final ApplicationEventPublisher publisher;

    @Autowired
    private SysJobService sysJobService;

    @SneakyThrows
    public void invokMethod(SysJob sysJob, Trigger trigger) {

        //执行开始时间
        long startTime;
        //执行结束时间
        long endTime;
        //获取执行开始时间
        startTime = System.currentTimeMillis();
        //更新定时任务表内的状态、执行时间、上次执行时间、下次执行时间等信息
        SysJob updateSysjob = new SysJob();
        updateSysjob.setJobId(sysJob.getJobId());
        //日志
        SysJobLog sysJobLog = new SysJobLog();
        sysJobLog.setJobId(sysJob.getJobId());
        sysJobLog.setJobName(sysJob.getJobName());
        sysJobLog.setJobGroup(sysJob.getJobGroup());
        sysJobLog.setJobOrder(sysJob.getJobOrder());
        sysJobLog.setJobType(sysJob.getJobType());
        sysJobLog.setExecutePath(sysJob.getExecutePath());
        sysJobLog.setClassName(sysJob.getClassName());
        sysJobLog.setMethodName(sysJob.getMethodName());
        sysJobLog.setMethodParamsValue(sysJob.getMethodParamsValue());
        sysJobLog.setCronExpression(sysJob.getCronExpression());
        sysJobLog.setTenantId(sysJob.getTenantId());
        try {
            //执行任务
            ITaskInvok iTaskInvok = TaskInvokFactory.getInvoker(sysJob.getJobType());
            iTaskInvok.invokMethod(sysJob);

            //记录成功状态
            sysJobLog.setJobMessage(QuartzEnum.JOB_LOG_STATUS_SUCCESS.getDescription());
            sysJobLog.setJobLogStatus(QuartzEnum.JOB_LOG_STATUS_SUCCESS.getType());
            //任务表信息更新
            updateSysjob.setJobExecuteStatus(QuartzEnum.JOB_LOG_STATUS_SUCCESS.getType());
        } catch (Throwable e) {
            log.error("定时任务执行失败，任务名称：{}；任务组名：{}，cron执行表达式：{}，执行时间：{}", sysJob.getJobName(), sysJob.getJobGroup(), sysJob.getCronExpression(), new Date());
            log.error(e.getMessage(), e);
            //记录失败状态
            sysJobLog.setJobMessage(QuartzEnum.JOB_LOG_STATUS_FAIL.getDescription());
            sysJobLog.setJobLogStatus(QuartzEnum.JOB_LOG_STATUS_FAIL.getType());
            sysJobLog.setExceptionInfo(StrUtil.sub(e.getMessage(), 0, 2000));
            //任务表信息更新
            updateSysjob.setJobExecuteStatus(QuartzEnum.JOB_LOG_STATUS_FAIL.getType());
        } finally {
            //记录执行时间 立刻执行使用的是simpleTeigger
            if (trigger instanceof CronTrigger) {
                updateSysjob.setStartTime(trigger.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                updateSysjob.setPreviousTime(trigger.getPreviousFireTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                updateSysjob.setNextTime(trigger.getNextFireTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            }
            //记录执行时长
            endTime = System.currentTimeMillis();
            sysJobLog.setExecuteTime(String.valueOf(endTime - startTime));

            publisher.publishEvent(new SysJobLogEvent(sysJobLog));
            sysJobService.updateById(updateSysjob);
        }
    }
}
