package com.kingthy.response;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

/**
 * @author:xumin
 * @Description:
 * @Date:17:45 2017/11/2
 */
@Data
@ToString
public class ClassCategoryResp implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private String topName;
    
    private String uuid;
    
    private String className;
}
