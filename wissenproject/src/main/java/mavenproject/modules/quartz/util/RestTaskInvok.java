package mavenproject.modules.quartz.util;

import mavenproject.common.utils.R;
import mavenproject.modules.quartz.entity.SysJob;
import mavenproject.modules.quartz.exception.TaskException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 定时任务rest反射实现
 *
 * @author
 */
@Slf4j
@Component("restTaskInvok")
@AllArgsConstructor
public class RestTaskInvok implements ITaskInvok {

    /**
     * 成功标记
     */
    public static final Integer SUCCESS = 0;
    /**
     * 失败标记
     */
    public static final Integer FAIL = 1;
    private RestTemplate restTemplate;

    @Override
    public void invokMethod(SysJob sysJob) throws TaskException {
        R r = restTemplate.getForObject(sysJob.getExecutePath(), R.class);
        Integer code = Integer.valueOf(r.get("code").toString());
        if (FAIL.equals(code)) {
            log.error("定时任务restTaskInvok异常,执行任务：{}", sysJob.getExecutePath());
            throw new TaskException("定时任务restTaskInvok业务执行失败,任务：" + sysJob.getExecutePath());
        }
    }
}
