package com.kingthy.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import java.io.Serializable;

/**
 * 
 * 
 * CreatePartsCategoryReq
 * 
 * @author 赵生辉 2017年4月13日 下午8:17:39
 * 
 * @version 1.0.0
 *
 */
@Data
@ToString
public class CreatePartsCategoryReq implements Serializable
{

    @ApiModelProperty("部件名称")
    private String className;

    private Boolean status;

    private String image;

    private Boolean delFlag;

    private String sn;

    private String type;

    private String modelFile;

    private static final long serialVersionUID = 1L;
    
}