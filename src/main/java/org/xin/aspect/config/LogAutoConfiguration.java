package org.xin.aspect.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xin.aspect.DefaultAroundApply;
import org.xin.aspect.aop.LogAspectAfterHandler;
import org.xin.aspect.aop.LogAspectAroundHandler;
import org.xin.aspect.aop.LogAspectBeforeHandler;

/**
 * @author huyuanxin
 */
@Configuration
public class LogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(LogAspectBeforeHandler.class)
    public LogAspectBeforeHandler logAspectBeforeHandler() {
        return new LogAspectBeforeHandler();
    }

    @Bean
    @ConditionalOnMissingBean(LogAspectAfterHandler.class)
    public LogAspectAfterHandler logAspectAfterHandler() {
        return new LogAspectAfterHandler();
    }

    @Bean
    @ConditionalOnMissingBean(LogAspectAroundHandler.class)
    public LogAspectAroundHandler logAspectAroundHandler() {
        return new LogAspectAroundHandler();
    }

    @Bean
    @ConditionalOnMissingBean(DefaultAroundApply.class)
    public DefaultAroundApply defaultAroundApply() {
        return new DefaultAroundApply();
    }

}
