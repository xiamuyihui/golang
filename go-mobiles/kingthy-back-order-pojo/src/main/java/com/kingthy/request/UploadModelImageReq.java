package com.kingthy.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * UploadModelImageReq(描述其作用)
 * <p>
 * @author 赵生辉 2017年06月13日 14:31
 *
 * @version 1.0.0
 */
@Data
@ToString
public class UploadModelImageReq implements Serializable
{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "正面照片")
    private String frontImage;

    @ApiModelProperty(name = "侧面照片")
    private String flankImage;

    @ApiModelProperty(name = "模型业务主键")
    private String modelUuid;

}
