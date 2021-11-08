package org.xin.aspect.domain;

import lombok.Data;
import org.xin.aspect.JoinPointHandler;
import org.xin.aspect.annotation.AspectAfter;
import org.xin.aspect.annotation.AspectBefore;

/**
 * @author huyuanxin
 */
@Data
public class AspectValue {

    Class<? extends JoinPointHandler>[] joinHandlerPointList;

    Boolean override;

    Boolean duplicable;

    private AspectValue(Class<? extends JoinPointHandler>[] joinHandlerPointList, Boolean override, Boolean duplicable) {
        this.joinHandlerPointList = joinHandlerPointList;
        this.override = override;
        this.duplicable = duplicable;
    }

    private AspectValue() {
    }

    public static AspectValue of(AspectBefore aspectBefore) {
        return new AspectValue(aspectBefore.joinHandlerPoints(), aspectBefore.override(), aspectBefore.duplicable());
    }

    public static AspectValue of(AspectAfter aspectAfter) {
        return new AspectValue(aspectAfter.joinHandlerPoints(), aspectAfter.override(), aspectAfter.duplicable());
    }

}
