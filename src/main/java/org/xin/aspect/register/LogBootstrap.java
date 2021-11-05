package org.xin.aspect.register;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.xin.aspect.JoinPointHandler;
import org.xin.aspect.exception.JoinPointHandlerLoadException;
import org.xin.aspect.exception.JoinPointHandlerLoadExceptionMessage;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

/**
 * @author huyuanxin
 */
@Slf4j
@Component
public class LogBootstrap implements ApplicationContextAware {

    @Resource
    private JoinPointHandlerRepository joinPointHandlerRepository;

    /**
     * applicationContext
     */
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        log.info("正在加载JoinPoint拦截器");
        Map<String, JoinPointHandler> handlerJoinPointMap = applicationContext.getBeansOfType(JoinPointHandler.class);
        handlerJoinPointMap.values().forEach(it -> {
            JoinPointHandler joinPointHandler = joinPointHandlerRepository.getHandlerJoinPointHashMap().put(handlerJoinPointClassName(it), it);
            if (joinPointHandler != null) {
                // 几乎不可能重复,防止有骚操作
                throw new JoinPointHandlerLoadException(JoinPointHandlerLoadExceptionMessage.DUPLICATE_JOIN_POINT_HANDLER);
            }
        });
        log.info("JoinPoint拦截器加载完成");
    }

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private String handlerJoinPointClassName(JoinPointHandler joinPointHandler) {
        String name = joinPointHandler.getClass().getName();
        return name.substring(0, name.indexOf("$"));
    }

}
