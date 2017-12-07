package com.kingthy.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * QueryAreaReq(描述其作用)
 * <p>
 * @author 赵生辉 2017年07月07日 10:03
 *
 * @version 1.0.0
 */
@Data
@ToString
public class QueryAreaReq implements Serializable
{
    private Integer pageNum;

    private Integer pageSize;

    private Integer grade;

    private String name;

    @JsonIgnore
    private String treePath;

    private Long areaParentId;

    private String fullName;

    private static final long serialVersionUID = 1L;
}
