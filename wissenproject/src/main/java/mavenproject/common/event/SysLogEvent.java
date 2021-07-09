package mavenproject.common.event;

import mavenproject.modules.sys.model.entity.SysLog;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author jinghong
 * 系统日志事件
 */
@Getter
@AllArgsConstructor
public class SysLogEvent {

    private final SysLog log;

}