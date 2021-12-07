package org.xin.aspect.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.xin.aspect.DefaultLogOutputHandlerImpl;
import org.xin.aspect.LogConstance;
import org.xin.aspect.LogOutputHandler;
import org.xin.aspect.annotation.LogAround;
import org.xin.aspect.domain.AnnotationInType;
import org.xin.aspect.domain.LogContent;
import org.xin.aspect.util.LogAnnotationUtil;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

/**
 * @author huyuanxin
 */
@Slf4j
@Aspect
@Component
public class LogAspectAroundHandler implements ApplicationContextAware {

    private final LogOutputHandler logOutputHandler;

    /**
     * applicationContext
     */
    private ApplicationContext applicationContext;

    public LogAspectAroundHandler(@Autowired(required = false) LogOutputHandler logOutputHandler) {
        this.logOutputHandler = logOutputHandler;
    }

    @Around(value = "@annotation(org.xin.aspect.annotation.LogAround)||@within(org.xin.aspect.annotation.LogAround)")
    public Object aroundHandler(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        AnnotationInType<LogAround> annotationInType = LogAnnotationUtil.findAnnotationInType(proceedingJoinPoint, LogAround.class);
        annotationInType.validation();

        LogAround annotationInMethod = annotationInType.getAnnotationInMethod();
        LogAround annotationInClass = annotationInType.getAnnotationInClass();

        // 如果类上无注解,直接执行方法上面的方法
        if (annotationInClass == null) {
            return handler(annotationInMethod, proceedingJoinPoint);
        }

        if (annotationInMethod == null) {
            return handler(annotationInClass, proceedingJoinPoint);
        }

        return handler(annotationInClass, annotationInMethod, proceedingJoinPoint);
    }

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 单个处理
     *
     * @param logAround           注解
     * @param proceedingJoinPoint 切入点
     * @return 结果
     * @throws Throwable 异常
     */
    private Object handler(LogAround logAround, ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        LogContent logContent = joinPointToLogContent(proceedingJoinPoint);

        logContent.setModuleName(logAround.moduleName());
        logContent.setOperationDescription(logAround.operationDescription());
        logContent.setOperationType(logAround.operationType());

        Object result = applicationContext.getBean(logAround.aroundApplyClass()).handlerAround(proceedingJoinPoint);
        logContent.setResult(result);

        logContentHandler(logContent);

        return result;
    }

    /**
     * 多个处理
     *
     * @param firstLogAround      优先的LogAround
     * @param secondLogAround     次优先的LogAround
     * @param proceedingJoinPoint 切入点
     * @return 结果
     * @throws Throwable 异常
     */
    private Object handler(LogAround firstLogAround, LogAround secondLogAround, ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        LogContent logContent = joinPointToLogContent(proceedingJoinPoint);

        logContent.setModuleName(getFirstNotDefault(firstLogAround.moduleName(), secondLogAround.moduleName(), LogConstance.DEFAULT_MODULE_NAME));
        logContent.setOperationDescription(getFirstNotDefault(firstLogAround.operationDescription(), secondLogAround.operationDescription(), LogConstance.DEFAULT_OPERATION_DESCRIPTION));
        logContent.setOperationType(getFirstNotDefault(firstLogAround.operationType(), secondLogAround.operationType(), LogConstance.DEFAULT_OPERATION_TYPE));

        Object result = applicationContext.getBean(firstLogAround.aroundApplyClass()).handlerAround(proceedingJoinPoint);
        logContent.setResult(result);

        logContentHandler(logContent);

        return result;
    }

    /**
     * 输出日志内容
     *
     * @param logContent 日志内容
     */
    private void logContentHandler(LogContent logContent) {
        if (logOutputHandler != null) {
            logOutputHandler.handlerLogOutput(logContent);
        }
        DefaultLogOutputHandlerImpl.getInstance().handlerLogOutput(logContent);
    }

    /**
     * 切入点转为日志内容
     *
     * @param proceedingJoinPoint 切入点
     * @return 日志内容
     */
    private LogContent joinPointToLogContent(ProceedingJoinPoint proceedingJoinPoint) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest servletRequest = Objects.requireNonNull(servletRequestAttributes).getRequest();

        LogContent logContent = new LogContent();

        logContent.setRequestIp(servletRequest.getRemoteAddr());
        logContent.setOperationMethod(proceedingJoinPoint.getSignature().getDeclaringTypeName() + "." + proceedingJoinPoint.getSignature().getName());
        logContent.setArgs(proceedingJoinPoint.getArgs());
        logContent.setRequestUrl(servletRequest.getRequestURL().toString());
        logContent.setRequestDate(new Date());

        return logContent;
    }

    /**
     * 拿到第一个不为默认值的
     *
     * @param first        优先的字符串
     * @param second       次优先的字符串
     * @param defaultValue 默认值
     * @return 值
     */
    private <T> T getFirstNotDefault(T first, T second, T defaultValue) {
        if (first.equals(defaultValue) && second.equals(defaultValue)) {
            return defaultValue;
        }

        if (first.equals(defaultValue)) {
            return second;
        }

        return first;
    }

}
