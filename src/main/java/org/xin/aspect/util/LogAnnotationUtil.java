package org.xin.aspect.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.xin.aspect.domain.AnnotationInType;

import java.lang.annotation.Annotation;

/**
 * @author huyuanxin
 */
public class LogAnnotationUtil {

    private LogAnnotationUtil() {

    }

    public static <T extends Annotation> AnnotationInType<T> findAnnotationInType(JoinPoint proceedingJoinPoint, Class<T> tClass) {
        Signature signature = proceedingJoinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;

        T annotationInMethod = methodSignature.getMethod().getDeclaredAnnotation(tClass);
        T annotationInClass = AnnotationUtils.findAnnotation(proceedingJoinPoint.getThis().getClass(), tClass);

        return new AnnotationInType<>(annotationInMethod, annotationInClass);
    }

}
