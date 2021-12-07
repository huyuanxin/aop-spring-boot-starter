package org.xin.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

/**
 * @author huyuanxin
 */
@Component
public class DefaultAroundApply implements AroundApply {

    /**
     * 处理Around
     *
     * @param point 切点
     * @return point.proceed()的结果
     * @throws Throwable point.proceed()抛出的错误
     */
    @Override
    public Object handlerAround(ProceedingJoinPoint point) throws Throwable {
        // do nothing
        return point.proceed();
    }

}
