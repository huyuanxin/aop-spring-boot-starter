package org.xin.aspect.domain;

import lombok.Data;
import org.xin.aspect.JoinPointHandler;

import java.util.List;

/**
 * @author huyuanxin
 */
@Data
public class JoinPointFinding {

    /**
     * 是否结束
     */
    private Boolean end;

    /**
     * 寻找的JoinPointHandler
     */
    private List<JoinPointHandler> joinPointHandlerList;

    public static JoinPointFinding of(Boolean end, List<JoinPointHandler> joinPointHandlerList) {
        return new JoinPointFinding(end, joinPointHandlerList);
    }

    private JoinPointFinding() {

    }

    private JoinPointFinding(Boolean end, List<JoinPointHandler> joinPointHandlerList) {
        this.end = end;
        this.joinPointHandlerList = joinPointHandlerList;
    }

}
