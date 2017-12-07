package com.kingthy.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.File;
import java.io.Serializable;

/**
 *
 * UploadModelmagReq
 * @author 赵生辉
 * @date 2017年06月15日 18:20
 *
 */
@Data
@ToString
public class UploadModelmagReq implements Serializable
{
    @ApiModelProperty("正面图")
    private String frontImage;

    @ApiModelProperty("侧面图")
    private String flankImage;

    /*@ApiModelProperty("用户模型业务主键")
    private String modelUuid;*/
}
