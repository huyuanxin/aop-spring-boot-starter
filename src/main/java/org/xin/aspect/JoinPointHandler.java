package org.xin.aspect;

import org.aspectj.lang.JoinPoint;

/**
 * 处理joinPoint接口
 * joinPoint handler interface
 *
 * @author huyuanxin
 * @since 1.0.0
 */
public interface JoinPointHandler {

    /**
     * 处理joinPoint
     * joinPoint handler
     *
     * @param joinPoint joinPoint
     */
    void handlerAop(JoinPoint joinPoint);

}
