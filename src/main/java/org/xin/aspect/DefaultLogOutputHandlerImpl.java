package org.xin.aspect;

import lombok.extern.slf4j.Slf4j;
import org.xin.aspect.domain.LogContent;

/**
 * @author huyuanxin
 */
@Slf4j
public class DefaultLogOutputHandlerImpl implements LogOutputHandler {

    private DefaultLogOutputHandlerImpl() {

    }

    public static DefaultLogOutputHandlerImpl getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 输出日志
     *
     * @param logContent 日志内容
     */
    @Override
    public void handlerLogOutput(LogContent logContent) {
        log.info(logContent.toString());
    }

    private static class Holder {
        private static final DefaultLogOutputHandlerImpl INSTANCE = new DefaultLogOutputHandlerImpl();
    }

}
