package mavenproject.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 系统配置类
 *
 * @author jinghong
 */
@Data
@Component
@ConfigurationProperties(prefix = JesimsProperties.PREFIX)
public class JesimsProperties {

    /**
     * 配置前缀
     */
    public static final String PREFIX = "jesims";

    /**
     * 安全相关配置
     */
    private SecurityProperties security = new SecurityProperties();
}
