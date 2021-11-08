package org.xin.aspect.aop;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.xin.aspect.JoinPointHandler;
import org.xin.aspect.annotation.AspectAfter;
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
public class LogAspectAfterHandler {

    @Resource
    private JoinPointHandlerRepository joinPointHandlerRepository;

    @SuppressWarnings("unchecked")
    @After(value = "@annotation(org.xin.aspect.annotation.AspectAfter)||@within(org.xin.aspect.annotation.AspectAfter)")
    public void doAfter(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        AspectAfter methodAnn = methodSignature.getMethod().getDeclaredAnnotation(AspectAfter.class);
        List<JoinPointHandler> joinPointHandlersInMethod = Lists.newArrayList();

        if (methodAnn != null) {
            joinPointHandlersInMethod = getJoinPointHandlersInAnnotation(methodAnn);
            if (methodAnn.override()) {
                joinPointHandlersInMethod.forEach(it -> it.handlerAop(joinPoint));
                return;
            }
        }

        Annotation declaredAnnotation = signature.getDeclaringType().getDeclaredAnnotation(AspectAfter.class);
        if (declaredAnnotation == null) {
            return;
        }
        if (declaredAnnotation instanceof AspectAfter) {
            AspectAfter classAnn = (AspectAfter) declaredAnnotation;
            List<JoinPointHandler> joinPointHandlersInClass = getJoinPointHandlersInAnnotation(classAnn);
            joinPointHandlersInClass.addAll(joinPointHandlersInMethod);
            joinPointHandlersInClass.forEach(it -> it.handlerAop(joinPoint));
        }
    }

    private List<JoinPointHandler> getJoinPointHandlersInAnnotation(AspectAfter aspectAfter) {
        if (aspectAfter == null) {
            return new ArrayList<>();
        }
        Class<? extends JoinPointHandler>[] classes = aspectAfter.joinHandlerPoints();
        if (classes.length == 0) {
            return new ArrayList<>();
        }
        List<JoinPointHandler> joinPointHandlers = joinPointHandlerRepository.getHandlerJoinPointsByClass(classes);
        if (!aspectAfter.duplicable()) {
            return joinPointHandlers.stream().distinct().collect(Collectors.toList());
        }
        return joinPointHandlers;
    }

}
