package org.xin.aspect;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xin.aspect.aop.LogAspectAfterHandler;
import org.xin.aspect.aop.LogAspectBeforeHandler;
import org.xin.aspect.register.JoinPointHandlerRepository;
import org.xin.aspect.register.LogBootstrap;

/**
 * @author huyuanxin
 */
@Configuration
public class LogAutoConfiguration {

    @Bean(initMethod = "init")
    @ConditionalOnMissingBean(LogBootstrap.class)
    public LogBootstrap logBootstrap() {
        return new LogBootstrap();
    }

    @Bean
    @ConditionalOnMissingBean(JoinPointHandlerRepository.class)
    public JoinPointHandlerRepository handlerJoinPointRepository() {
        return new JoinPointHandlerRepository();
    }

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

}
