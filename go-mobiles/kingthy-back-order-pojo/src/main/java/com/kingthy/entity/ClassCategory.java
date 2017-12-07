package com.kingthy.entity;

import com.kingthy.common.BaseTableFileds;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author:xumin
 * @Description:
 * @Date:17:45 2017/11/2
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class ClassCategory extends BaseTableFileds implements Serializable
{
    
    private Integer orders;
    
    private Integer grade;
    
    private String parentId;
    
    private String className;
    
    private String treePath;
    
    private Boolean status;
    
    private String description;

    private String code;
    
    private static final long serialVersionUID = 1L;
}