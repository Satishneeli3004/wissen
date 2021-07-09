package mavenproject.common.exception;

/**
 * 枚举异常
 *
 * @author jinghong
 */
public class UnknownEnumException extends RuntimeException {

    public UnknownEnumException() {
        super();
    }

    public UnknownEnumException(String message) {
        super(message);
    }

    public UnknownEnumException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownEnumException(Throwable cause) {
        super(cause);
    }

    protected UnknownEnumException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
