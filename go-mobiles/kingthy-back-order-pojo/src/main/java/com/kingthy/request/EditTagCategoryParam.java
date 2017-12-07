package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 
 *
 * EditTagCategoryParam
 * 
 * @author 赵生辉 2017年4月13日 下午8:19:53
 * 
 * @version 1.0.0
 *
 */
@ToString
@Data
public class EditTagCategoryParam implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 业务主键
     */
    private String uuid;
    
    /**
     * 名称
     */
    private String className;
    
    /**
     * 状态
     */
    private Boolean status;
    
    /**
     * 描述
     */
    private String description;
    
}