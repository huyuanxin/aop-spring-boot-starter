package org.xin.aspect.annotation;

import org.xin.aspect.AroundApply;
import org.xin.aspect.DefaultAroundApply;
import org.xin.aspect.LogConstance;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author huyuanxin
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAround {

    /**
     * @return 日志模块名称
     */
    String moduleName() default LogConstance.DEFAULT_MODULE_NAME;

    /**
     * @return 操作类型
     */
    String operationType() default LogConstance.DEFAULT_OPERATION_TYPE;

    /**
     * @return 操作描述
     */
    String operationDescription() default LogConstance.DEFAULT_OPERATION_DESCRIPTION;


    Class<? extends AroundApply> aroundApplyClass() default DefaultAroundApply.class;

}
