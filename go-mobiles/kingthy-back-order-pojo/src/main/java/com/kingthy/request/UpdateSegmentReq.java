package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * UpdateSegmentReq(描述其作用)
 * <p>
 * @author 赵生辉 2017年07月11日 16:29
 *
 * @version 1.0.0
 */
@Data
@ToString
public class UpdateSegmentReq implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String uuid;

    private String className;

    private Boolean status;

    private Integer originValue;

    private Integer terminalValue;

    private String description;
}
