package com.kingthy.request;

import lombok.Data;
import lombok.ToString;


import java.io.Serializable;

/**
 * 
 *
 * CategoryReq(品类添加或修改的入参封装类)
 * 
 * @author 陈钊 2017年4月1日 上午11:53:11
 * 
 * @version 1.0.0
 *
 */
@Data
@ToString
public class CategoryReq implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    private String parentId;

    private String className;
    
    private Boolean status;
    
    private String description;
    
    private String parentGrade;
    
    private String uuid;
    
}
