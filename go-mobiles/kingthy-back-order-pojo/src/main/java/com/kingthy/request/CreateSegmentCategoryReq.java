package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * CreateSegmentCategoryReq(描述其作用)
 * <p>
 * @author 赵生辉 2017年07月11日 13:47
 *
 * @version 1.0.0
 */
@Data
@ToString
public class CreateSegmentCategoryReq implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String className;

    private Boolean status;

    private Integer originValue;

    private Integer terminalValue;

    private String description;

    private Integer type;
}
