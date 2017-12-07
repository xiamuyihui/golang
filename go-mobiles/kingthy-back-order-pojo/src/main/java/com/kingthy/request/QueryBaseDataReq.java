package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * BaseDataReq(描述其作用)
 * <p>
 * @author 赵生辉 2017年05月11日 11:17
 *
 * @version 1.0.0
 */
@Data
@ToString
public class QueryBaseDataReq implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer pageNum;

    private Integer pageSize;

    private String className;

    private Boolean status;

    private String grade;

    private String parentId;

    private Integer type;

}
