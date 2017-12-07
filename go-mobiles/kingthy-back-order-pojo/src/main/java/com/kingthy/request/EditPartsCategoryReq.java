package com.kingthy.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import java.io.Serializable;

/**
 * 
 *
 * EditPartsCategoryReq
 * 
 * @author 赵生辉 2017年4月13日 下午8:19:45
 * 
 * @version 1.0.0
 *
 */
@Data
@ToString
public class EditPartsCategoryReq implements Serializable
{
    
    private static final long serialVersionUID = 1L;

    private String uuid;

    @ApiModelProperty
    private String className;

    private Boolean status;

    private String image;

    private String type;

    private String modelFile;
}
