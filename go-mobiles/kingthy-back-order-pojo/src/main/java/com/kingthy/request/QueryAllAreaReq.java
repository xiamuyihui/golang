package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * QueryAllAreaReq(描述其作用)
 * <p>
 * @author 赵生辉 2017年07月20日 16:40
 *
 * @version 1.0.0
 */
@Data
@ToString
public class QueryAllAreaReq implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer grade;

    private Integer areaParentId;

    private String name;
}
