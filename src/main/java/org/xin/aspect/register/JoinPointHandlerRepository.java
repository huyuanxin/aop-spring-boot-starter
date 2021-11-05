package org.xin.aspect.register;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.xin.aspect.JoinPointHandler;
import org.xin.aspect.exception.JoinPointHandlerLoadException;
import org.xin.aspect.exception.JoinPointHandlerLoadExceptionMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huyuanxin
 */
@Data
@Component
public class JoinPointHandlerRepository {

    private Map<String, JoinPointHandler> handlerJoinPointHashMap = new HashMap<>();

    /**
     * 通过class或者HandlerJoinPoint实现
     *
     * @param classes classes
     * @return HandlerJoinPoint实现
     */
    @SafeVarargs
    public final List<JoinPointHandler> getHandlerJoinPointsByClass(Class<? extends JoinPointHandler>... classes) {
        List<JoinPointHandler> joinPointHandlerList = new ArrayList<>();
        if (classes.length == 0) {
            return joinPointHandlerList;
        }
        for (Class<? extends JoinPointHandler> clz : classes) {
            JoinPointHandler joinPointHandler = handlerJoinPointHashMap.get(clz.getName());
            if (joinPointHandler != null) {
                joinPointHandlerList.add(joinPointHandler);
            }
        }
        if (CollectionUtils.isEmpty(joinPointHandlerList)) {
            throw new JoinPointHandlerLoadException(JoinPointHandlerLoadExceptionMessage.JOIN_POINT_HANDLER_NOT_FOUND);
        }
        if (joinPointHandlerList.size() != classes.length) {
            throw new JoinPointHandlerLoadException(JoinPointHandlerLoadExceptionMessage.JOIN_POINT_HANDLER_NUMBER_NOT_MATCH);
        }
        return joinPointHandlerList;
    }

}
