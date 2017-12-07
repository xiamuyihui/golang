package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * QueryParentAreaReq(描述其作用)
 * <p>
 * @author 赵生辉 2017年07月11日 15:18
 *
 * @version 1.0.0
 */
@Data
@ToString
public class QueryParentAreaReq implements Serializable
{
    private static final long serialVersionUID = 1L;

    private int pageNum;

    private int pageSize;

    private int parentId;
}
