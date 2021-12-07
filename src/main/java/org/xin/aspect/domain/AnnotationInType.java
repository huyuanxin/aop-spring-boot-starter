package org.xin.aspect.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.annotation.Annotation;

/**
 * @author huyuanxin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnotationInType<T extends Annotation> {

    T annotationInMethod;

    T annotationInClass;

    /**
     * 校验
     */
    public void validation() {
        if (annotationInMethod == null && annotationInClass == null) {
            throw new NullPointerException("没有注解却进入这个拦截器");
        }
    }

}
