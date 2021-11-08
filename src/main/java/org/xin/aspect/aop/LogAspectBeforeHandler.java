package org.xin.aspect.aop;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.xin.aspect.JoinPointHandler;
import org.xin.aspect.annotation.AspectBefore;
import org.xin.aspect.register.JoinPointHandlerRepository;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huyuanxin
 */
@Slf4j
@Aspect
@Component
public class LogAspectBeforeHandler {

    @Resource
    private JoinPointHandlerRepository joinPointHandlerRepository;

    @SuppressWarnings("unchecked")
    @Before(value = "@annotation(org.xin.aspect.annotation.AspectBefore)||@within(org.xin.aspect.annotation.AspectBefore)")
    public void doBefore(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        AspectBefore methodAnn = methodSignature.getMethod().getDeclaredAnnotation(AspectBefore.class);
        List<JoinPointHandler> joinPointHandlersInMethod = Lists.newArrayList();

        if (methodAnn != null) {
            joinPointHandlersInMethod = getJoinPointHandlersInAnnotation(methodAnn);
            if (methodAnn.override()) {
                joinPointHandlersInMethod.forEach(it -> it.handlerAop(joinPoint));
                return;
            }
        }

        Annotation declaredAnnotation = signature.getDeclaringType().getDeclaredAnnotation(AspectBefore.class);
        if (declaredAnnotation == null) {
            return;
        }
        if (declaredAnnotation instanceof AspectBefore) {
            AspectBefore classAnn = (AspectBefore) declaredAnnotation;
            List<JoinPointHandler> joinPointHandlersInClass = getJoinPointHandlersInAnnotation(classAnn);
            joinPointHandlersInClass.addAll(joinPointHandlersInMethod);
            joinPointHandlersInClass.forEach(it -> it.handlerAop(joinPoint));
        }
    }

    private List<JoinPointHandler> getJoinPointHandlersInAnnotation(AspectBefore aspectBefore) {
        if (aspectBefore == null) {
            return new ArrayList<>();
        }
        Class<? extends JoinPointHandler>[] classes = aspectBefore.joinHandlerPoints();
        if (classes.length == 0) {
            return new ArrayList<>();
        }
        List<JoinPointHandler> joinPointHandlers = joinPointHandlerRepository.getHandlerJoinPointsByClass(classes);
        if (!aspectBefore.duplicable()) {
            return joinPointHandlers.stream().distinct().collect(Collectors.toList());
        }
        return joinPointHandlers;
    }

}
