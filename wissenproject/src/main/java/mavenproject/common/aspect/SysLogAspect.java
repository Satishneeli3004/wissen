package mavenproject.common.aspect;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONUtil;
import mavenproject.common.annotation.Log;
import mavenproject.common.event.SysLogEvent;
import mavenproject.common.security.util.SecurityUtils;
import mavenproject.common.utils.SpringContextHolder;
import mavenproject.modules.sys.model.entity.SysLog;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 日志切面类
 *
 * @author jinghong
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class SysLogAspect {

    private final ApplicationEventPublisher publisher;

    /**
     * 拦截所有加了{@link mavenproject.common.annotation.Log}注解的方法
     *
     * @param point
     * @param sysLog
     * @return
     */
    @SneakyThrows
    @Around("@annotation(sysLog)")
    public Object around(ProceedingJoinPoint point, Log sysLog) {
        Long startTime = System.currentTimeMillis();
        String className = point.getTarget().getClass().getSimpleName();
        String methodName = point.getSignature().getName();
        HttpServletRequest request = SpringContextHolder.getRequest();
        SysLog logVo = new SysLog();
        try {
            Object[] args = point.getArgs();
            if (args != null) {
                // 参数排除掉request response 和file
                List<Object> argList = Arrays.stream(args).filter(arg -> !(arg instanceof ServletRequest) && !(arg instanceof ServletResponse) && !(arg instanceof MultipartFile)).collect(Collectors.toList());
                if (CollUtil.isNotEmpty(argList)) {
                    String jsonObject = JSONUtil.toJsonStr(argList);
                    logVo.setParams(jsonObject);
                }
            }
        } catch (Exception e) {
            log.error("解析参数失败", e);
        }
        try {
            return point.proceed();
        } finally {
            // 描述
            logVo.setOperation(sysLog.value());
            // 操作时间
            Long endTime = System.currentTimeMillis();
            logVo.setTime(endTime - startTime);
            // ip
            String clientIP = ServletUtil.getClientIP(request);
            boolean innerIP = NetUtil.isInnerIP(clientIP);
            logVo.setIp(clientIP);
            // 访问的方法名称
            logVo.setMethod(className + "." + methodName + "()");
            String userAgent = request.getHeader("User-Agent");
            UserAgent ua = UserAgentUtil.parse(userAgent);
            // 设备名称
            logVo.setOsName(ua.getOs().toString());
            try {
                // 用户名
                logVo.setUsername(SecurityUtils.getUser().getName());
            } catch (Exception e) {
            }
            // 访问地址
            logVo.setUrl(request.getRequestURL().toString());
            logVo.setDevice(ua.getPlatform().toString());
            // 浏览器类型
            logVo.setBrowserType(ua.getBrowser().toString());

            // 发送异步日志事件
            publisher.publishEvent(new SysLogEvent(logVo));
        }

    }

}