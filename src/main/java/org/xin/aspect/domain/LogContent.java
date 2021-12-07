package org.xin.aspect.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author huyuanxin
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LogContent {

    /**
     * 功能模块
     */
    private String moduleName;

    /**
     * 操作类型
     */
    private String operationType;

    /**
     * 操作描述
     */
    private String operationDescription;

    /**
     * 请求参数
     */
    private Object[] args;

    /**
     * 结果
     */
    private Object result;

    /**
     *
     */
    private Long operatorId;

    /**
     * 操作方法
     */
    private String operationMethod;

    /**
     * 请求URL
     */
    private String requestUrl;

    /**
     * 请求IP
     */
    private String requestIp;

    /**
     * 操作时间
     */
    private Date requestDate;

}
