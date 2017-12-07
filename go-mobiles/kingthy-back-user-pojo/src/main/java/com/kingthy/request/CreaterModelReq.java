package com.kingthy.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 *
 * createrModelReq
 * @author 赵生辉
 * @date  2017/9/11.
 *
 */
@Data
@ToString
public class CreaterModelReq implements Serializable
{
    private static final long serialVersionUID = 1L;


    @ApiModelProperty("会员当前登录的token")
    private String token;

    @ApiModelProperty("模型名称")
    private String modelName;

    @ApiModelProperty("三维模型文件")
    private String modelFile;

    @ApiModelProperty("图片文件")
    private String modelImage;

    @ApiModelProperty("是否默认")
    private Boolean isDefault;
}
