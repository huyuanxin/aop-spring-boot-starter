package org.xin.aspect.exception;

/**
 * @author huyuanxin
 */
public abstract class JoinPointHandlerLoadExceptionMessage {

    public static final String DUPLICATE_JOIN_POINT_HANDLER = "重复的JoinPointHandler";

    public static final String JOIN_POINT_HANDLER_NOT_FOUND = "未加载到JoinPointHandler";

    public static final String JOIN_POINT_HANDLER_NUMBER_NOT_MATCH = "JoinPointHandler数量不匹配";

    private JoinPointHandlerLoadExceptionMessage() {

    }

}
