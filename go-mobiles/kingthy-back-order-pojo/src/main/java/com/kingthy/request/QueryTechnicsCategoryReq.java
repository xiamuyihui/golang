package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * CreateTechnicsCategoryReq(描述其作用)
 * <p>
 * @author 赵生辉 2017年07月28日 18:21
 *
 * @version 1.0.0
 */
@Data
@ToString
public class QueryTechnicsCategoryReq implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer pageNum;

    private Integer pageSize;
    /**
     * 名称
     */
    private String name;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 工艺类型
     */
    private Integer type;

    /**
     * 描述
     */
    private String description;


}
