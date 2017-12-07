package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * QuerySegmentPageReq(描述其作用)
 * <p>
 * @author 赵生辉 2017年07月11日 14:24
 *
 * @version 1.0.0
 */
@Data
@ToString
public class QuerySegmentPageReq implements Serializable
{
    private int pageNum;

    private Integer type;

    private int pageSize;

    private String className;

    private Boolean status;

    private Integer originValue;

    private Integer terminalValue;

    private String description;
}
