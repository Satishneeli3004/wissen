package mavenproject.common.handler;

import cn.hutool.core.util.StrUtil;
import mavenproject.common.exception.UnknownEnumException;
import mavenproject.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author jinghong
 */
@Slf4j
@RestControllerAdvice
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class GlobalExceptionHandler {

    @Value("${spring.profiles.active:dev}")
    private String active;

    /**
     * 默认异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public R handleBadRequest(Exception e) {

        Throwable cause = e.getCause();
        if (cause != null) {
            // 枚举异常
            Throwable throwable = cause.getCause();
            if (throwable != null && throwable instanceof UnknownEnumException) {
                return R.failed(throwable.getMessage());
            }
        }

        log.error("系统内部错误", e);
        // 开发环境才输出错误信息，其他环境返回`系统内部错误`
        if (!StrUtil.equals(active, "dev")) {
            return R.failed("系统内部错误");
        }

        return R.failed(e.getMessage());

    }

    /**
     * 请求格式不支持
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class, HttpMediaTypeNotSupportedException.class})
    public R handleNotSupportedException(Exception e) {
        log.error("系统内部错误", e);
        return R.failed("请求格式不支持");
    }

    /**
     * 数据校验异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleBadRequest(MethodArgumentNotValidException e) {
        // 取第一条到页面展示
        ObjectError objectError = e.getBindingResult().getAllErrors().iterator().next();
        return R.failed(((FieldError) objectError).getField(), objectError.getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AccessDeniedException.class)
    public R handleAccessDeniedException(AccessDeniedException e) {
        return R.failed("用户权限不足");
    }

    /**
     * 绑定异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    public R handleBindException(BindException e) {
        // 取第一条到页面展示
        ObjectError objectError = e.getBindingResult().getAllErrors().iterator().next();
        return R.failed(((FieldError) objectError).getField(), objectError.getDefaultMessage());
    }

    /**
     * 参数异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public R handleIllegalArgumentException(IllegalArgumentException e) {
        return R.failed(e.getMessage());

    }
}

