package mavenproject.modules.quartz.event;

import mavenproject.modules.quartz.entity.SysJob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.quartz.Trigger;

/**
 * 定时任务多线程事件
 *
 * @author jinghong
 */
@Getter
@AllArgsConstructor
public class SysJobEvent {

    private final SysJob sysJob;

    private final Trigger trigger;
}
