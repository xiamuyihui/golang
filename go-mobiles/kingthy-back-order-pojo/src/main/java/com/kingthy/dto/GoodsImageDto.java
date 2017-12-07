package com.kingthy.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * GoodsImageDto(描述其作用)
 * <p>
 * @author 赵生辉 2017年06月30日 17:02
 *
 * @version 1.0.0
 */
@Data
@ToString
public class GoodsImageDto implements Serializable
{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "作品图")
    private String opusImage;

    @ApiModelProperty(value = "最大图")
    private String maxImg;

    @ApiModelProperty(value = "最小图")
    private String minImg;

    @ApiModelProperty(value = "中图")
    private String midImg;

    @ApiModelProperty(value = "封面图")
    private String isCover;

}
