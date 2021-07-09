package mavenproject.modules.quartz.config;

import mavenproject.modules.quartz.entity.SysJob;
import mavenproject.modules.quartz.event.SysJobEvent;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.quartz.Trigger;
import org.springframework.context.ApplicationEventPublisher;

/**
 * @author
 */
@Aspect
@Slf4j
@AllArgsConstructor
public class QuartzInvokeFactory {

    private final ApplicationEventPublisher publisher;

    @SneakyThrows
    void init(SysJob sysJob, Trigger trigger) {
        publisher.publishEvent(new SysJobEvent(sysJob, trigger));
    }
}
