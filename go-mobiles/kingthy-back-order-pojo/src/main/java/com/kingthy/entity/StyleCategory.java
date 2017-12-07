package com.kingthy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kingthy.common.BaseTableFileds;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
/**
 * @author:xumin
 * @Description:
 * @Date:18:14 2017/11/2
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class StyleCategory extends BaseTableFileds implements Serializable
{

    @JsonIgnore
    private Integer orders;

    private Integer grade;

    private String parentId;

    private String className;

    @JsonIgnore
    private String treePath;

    private Boolean status;

    private String description;

    private Integer goodsNum;
    
    private static final long serialVersionUID = 1L;
    
}