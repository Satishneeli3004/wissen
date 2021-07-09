package mavenproject.common.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 *
 * @author jinghong
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 操作的内容
     *
     * @return
     */
    String value();
}