package org.xin.aspect.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.xin.aspect.JoinPointHandler;
import org.xin.aspect.annotation.AspectAfter;
import org.xin.aspect.domain.AspectValue;
import org.xin.aspect.domain.JoinPointFinding;
import org.xin.aspect.register.JoinPointHandlerRepository;
import org.xin.aspect.utils.AopUtils;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.util.List;

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

        JoinPointFinding joinPointFinding = AopUtils.getJoinPointHandlersInMethod(joinPointHandlerRepository, AspectValue.of(methodAnn));
        if (Boolean.TRUE.equals(joinPointFinding.getEnd())) {
            joinPointFinding.getJoinPointHandlerList().forEach(it -> it.handlerAop(joinPoint));
            return;
        }

        Annotation declaredAnnotation = signature.getDeclaringType().getDeclaredAnnotation(AspectAfter.class);
        if (declaredAnnotation == null) {
            joinPointFinding.getJoinPointHandlerList().forEach(it -> it.handlerAop(joinPoint));
            return;
        }

        if (declaredAnnotation instanceof AspectAfter) {
            AspectAfter classAnn = (AspectAfter) declaredAnnotation;
            List<JoinPointHandler> joinPointHandlersInClass = AopUtils.getJoinPointHandlersInAnnotation(joinPointHandlerRepository, AspectValue.of(classAnn));
            joinPointHandlersInClass.addAll(joinPointFinding.getJoinPointHandlerList());
            joinPointHandlersInClass.forEach(it -> it.handlerAop(joinPoint));
        }
    }

}
