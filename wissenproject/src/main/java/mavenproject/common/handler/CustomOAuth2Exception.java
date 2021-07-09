package mavenproject.common.handler;

import cn.hutool.json.JSONUtil;
import mavenproject.common.exception.CustomOAuth2ExceptionJackson2Serializer;
import mavenproject.common.utils.R;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * 自定义OAuth2Exception异常类
 *
 * @author jinghong
 */
@com.fasterxml.jackson.databind.annotation.JsonSerialize(using = CustomOAuth2ExceptionJackson2Serializer.class)
public class CustomOAuth2Exception extends OAuth2Exception {
    public CustomOAuth2Exception(String msg, Throwable t) {
        super(msg, t);
    }

    public CustomOAuth2Exception(String msg) {
        super(msg);
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(R.failed(this.getMessage()));
    }
}
