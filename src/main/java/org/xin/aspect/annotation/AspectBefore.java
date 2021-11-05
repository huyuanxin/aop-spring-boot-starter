package org.xin.aspect.annotation;

import org.xin.aspect.JoinPointHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * LogAspectAnnotation
 * 日志切点注解
 *
 * @author huyuanxin
 * @since 1.0.0
 */
@SuppressWarnings("unused")
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface AspectBefore {

    /**
     * HandlerJoinPoint实现类的class
     * <p>
     * HandlerJoinPoint implement class Array
     *
     * @return {@linkplain JoinPointHandler}
     */
    Class<? extends JoinPointHandler>[] joinHandlerPoints() default {};

    /**
     * is override {@linkplain AspectBefore} annotation {@linkplain JoinPointHandler} value in Class
     * <p>
     * 是否覆盖类上的{@linkplain AspectBefore}的 {@linkplain JoinPointHandler}
     *
     * @return is override
     */
    boolean override() default true;

    /**
     * 是否可以重复
     * <p>
     * is duplicable
     *
     * @return is duplicable
     */
    boolean duplicable() default false;

}
