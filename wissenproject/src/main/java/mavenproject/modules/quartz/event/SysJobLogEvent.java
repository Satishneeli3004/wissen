package mavenproject.modules.quartz.event;

import mavenproject.modules.quartz.entity.SysJobLog;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 定时任务日志多线程事件
 *
 * @author jinghong
 */
@Getter
@AllArgsConstructor
public class SysJobLogEvent {
    private final SysJobLog sysJobLog;
}
