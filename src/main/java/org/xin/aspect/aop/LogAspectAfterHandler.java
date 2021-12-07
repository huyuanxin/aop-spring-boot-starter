package org.xin.aspect.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.xin.aspect.JoinPointHandler;
import org.xin.aspect.annotation.AspectAfter;
import org.xin.aspect.domain.AnnotationInType;
import org.xin.aspect.util.LogAnnotationUtil;

import javax.annotation.Nonnull;
import java.util.Arrays;

/**
 * @author huyuanxin
 */
@Slf4j
@Aspect
@Component
public class LogAspectAfterHandler implements ApplicationContextAware {

    /**
     * applicationContext
     */
    private ApplicationContext applicationContext;

    @After(value = "@annotation(org.xin.aspect.annotation.AspectAfter)||@within(org.xin.aspect.annotation.AspectAfter)")
    public void doAfter(JoinPoint joinPoint) {

        AnnotationInType<AspectAfter> annotationInType = LogAnnotationUtil.findAnnotationInType(joinPoint, AspectAfter.class);
        annotationInType.validation();

        AspectAfter annotationInMethod = annotationInType.getAnnotationInMethod();
        AspectAfter annotationInClass = annotationInType.getAnnotationInClass();

        if (annotationInMethod == null) {
            Arrays.stream(annotationInClass.joinHandlerPoints()).forEach(it -> {
                JoinPointHandler joinPointHandler = applicationContext.getBean(it);
                joinPointHandler.handlerAop(joinPoint);
            });
            return;
        }

        if (annotationInClass == null) {
            Arrays.stream(annotationInMethod.joinHandlerPoints()).forEach(it -> {
                JoinPointHandler joinPointHandler = applicationContext.getBean(it);
                joinPointHandler.handlerAop(joinPoint);
            });
            return;
        }

        if (annotationInMethod.override()) {
            Arrays.stream(annotationInMethod.joinHandlerPoints()).forEach(it -> {
                JoinPointHandler joinPointHandler = applicationContext.getBean(it);
                joinPointHandler.handlerAop(joinPoint);
            });
        } else {
            Arrays.stream(annotationInClass.joinHandlerPoints()).forEach(it -> {
                JoinPointHandler joinPointHandler = applicationContext.getBean(it);
                joinPointHandler.handlerAop(joinPoint);
            });
            Arrays.stream(annotationInMethod.joinHandlerPoints()).forEach(it -> {
                JoinPointHandler joinPointHandler = applicationContext.getBean(it);
                joinPointHandler.handlerAop(joinPoint);
            });
        }

    }

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
