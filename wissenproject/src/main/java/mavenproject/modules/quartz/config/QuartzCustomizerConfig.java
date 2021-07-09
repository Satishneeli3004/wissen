package mavenproject.modules.quartz.config;

import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author
 */
@Configuration
public class QuartzCustomizerConfig implements SchedulerFactoryBeanCustomizer {
    @Override
    public void customize(SchedulerFactoryBean schedulerFactoryBean) {
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);
    }
}
