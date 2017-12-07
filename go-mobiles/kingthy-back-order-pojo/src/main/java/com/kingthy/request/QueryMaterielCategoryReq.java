package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * QueryMaterielCategoryReq(描述其作用)
 * <p>
 * @author 赵生辉 2017年07月14日 18:07
 *
 * @version 1.0.0
 */
@Data
@ToString
public class QueryMaterielCategoryReq implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer pageNum;

    private Integer pageSize;

    private String className;

    private Boolean status;
}
