package org.xin.aspect.utils;

import org.xin.aspect.JoinPointHandler;
import org.xin.aspect.domain.AspectValue;
import org.xin.aspect.domain.JoinPointFinding;
import org.xin.aspect.register.JoinPointHandlerRepository;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huyuanxin
 */
public class AopUtils {

    private AopUtils() {

    }

    /**
     * 获得joinPointHandler List
     *
     * @param joinPointHandlerRepository 注册repository
     * @param aspectValue                aspectValue
     * @return joinPointHandler List
     */
    public static List<JoinPointHandler> getJoinPointHandlersInAnnotation(@Nonnull JoinPointHandlerRepository joinPointHandlerRepository, AspectValue aspectValue) {
        if (aspectValue.getJoinHandlerPointList().length == 0) {
            return new ArrayList<>();
        }

        List<JoinPointHandler> joinPointHandlers = joinPointHandlerRepository.getHandlerJoinPointsByClass(aspectValue.getJoinHandlerPointList());
        if (aspectValue.getDuplicable()) {
            return joinPointHandlers;
        }

        return joinPointHandlers.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 拿到Method注解里面的JoinPointHandlers并封装成JoinPointFinding
     *
     * @param joinPointHandlerRepository 注册repository
     * @param aspectValue                aspectValue
     * @return JoinPointFinding
     */
    public static JoinPointFinding getJoinPointHandlersInMethod(@Nonnull JoinPointHandlerRepository joinPointHandlerRepository, AspectValue aspectValue) {
        return JoinPointFinding.of(aspectValue.getOverride(), getJoinPointHandlersInAnnotation(joinPointHandlerRepository, aspectValue));
    }

}
