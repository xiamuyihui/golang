package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * QueryPartsCategoryReq(描述其作用)
 * <p>
 * @author chenz 2017年09月19日 17:44
 *
 * @version 1.0.0
 */
@Data
@ToString
public class QueryCraftsPartsCategoryReq implements Serializable
{
    private static final long serialVersionUID = 1L;

    private int pageNum;

    private int pageSize;

    private String sn;

    private String className;

    private String type;

//    private String token;
}
