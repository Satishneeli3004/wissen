package mavenproject.modules.quartz.controller;

import mavenproject.common.utils.R;
import mavenproject.modules.framework.controller.BaseController;
import mavenproject.modules.quartz.entity.SysJobLog;
import mavenproject.modules.quartz.service.SysJobLogService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author jjh
 * <p>
 * 定时任务执行日志表
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sys-job-log")
public class SysJobLogController extends BaseController {
    private final SysJobLogService sysJobLogService;

    /**
     * 分页查询
     *
     * @param sysJobLog 定时任务执行日志表
     * @return
     */
    @GetMapping("/page")
    public R getSysJobLogPage(SysJobLog sysJobLog) {
        return R.ok(sysJobLogService.page(getPage(), Wrappers.query(sysJobLog)));
    }
}
