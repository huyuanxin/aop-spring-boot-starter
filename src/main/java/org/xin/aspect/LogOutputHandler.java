package org.xin.aspect;

import org.xin.aspect.domain.LogContent;

/**
 * @author huyuanxin
 */
public interface LogOutputHandler {

    /**
     * 输出日志
     *
     * @param logContent 日志内容
     */
    void handlerLogOutput(LogContent logContent);

}
