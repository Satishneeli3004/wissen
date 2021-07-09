package mavenproject.modules.quartz.exception;

/**
 * 定时任务异常
 *
 * @author jinghong
 */
public class TaskException extends Exception {

    public TaskException() {
        super();
    }

    public TaskException(String msg) {
        super(msg);
    }
}
