package mavenproject.common.config;

import lombok.Data;

/**
 * security属性类
 *
 * @author jinghong
 */
@Data
public class SecurityProperties {

    /**
     * 需要忽略的地址
     */
    private String[] ignoring;

    /**
     * 社交登录clientId
     */
    private String socialClientId;
}
