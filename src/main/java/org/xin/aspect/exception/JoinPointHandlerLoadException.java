package org.xin.aspect.exception;

/**
 * HandlerJoinPoint加载异常,通常为加载不到或者重复加载
 *
 * @author huyuanxin
 */
@SuppressWarnings("unused")
public class JoinPointHandlerLoadException extends RuntimeException {

    private static final long serialVersionUID = 1;

    public JoinPointHandlerLoadException() {

    }

    public JoinPointHandlerLoadException(String s) {
        super(s);
    }

}
