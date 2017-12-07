package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 
 *
 * CategoryTreeDto(品类构造树结构Bean)
 * 
 * @author 陈钊 2017年4月11日 下午7:38:35
 * 
 * @version 1.0.0
 *
 */
@Data
@ToString
public class CategoryTreeDto implements Serializable
{
    
    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     *
     * @since 1.0.0
     */
    
    private static final long serialVersionUID = 1L;
    
    private Integer grade;
    
    private String uuid;
    
    private String parentId;
    
    private String className;
    
    /**
     * 子孙节点
     */
    private List<CategoryTreeDto> childrens;
    
}