package mavenproject.modules.quartz.task;

import mavenproject.modules.quartz.constant.enums.QuartzEnum;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author jinghong 2019/10/17 10:31
 */
@Component
@Slf4j
public class DemoTask {

    /**
     * 测试Spring Bean的演示方法
     */
    @SneakyThrows
    public String demoMethod(String para) {
        log.info("测试于:{}，输入参数{}", LocalDateTime.now(), para);
        return QuartzEnum.JOB_LOG_STATUS_SUCCESS.getType();
    }
}
