package org.xin.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author huyuanxin
 */
public interface AroundApply {

    /**
     * 处理Around
     *
     * @param proceedingJoinPoint 切点
     * @return point.proceed()的结果
     * @throws Throwable point.proceed()抛出的错误
     */
    Object handlerAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable;

}
