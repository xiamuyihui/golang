package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 
 *
 * CreateTagCategoryReq
 * 
 * @author 赵生辉 2017年4月13日 下午8:18:04
 * 
 * @version 1.0.0
 *
 */
@ToString
@Data
public class CreateTagCategoryReq implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
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
