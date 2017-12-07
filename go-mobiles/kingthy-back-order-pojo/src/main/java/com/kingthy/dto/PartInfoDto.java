package com.kingthy.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * PartInfoDto(描述其作用)
 * <p>
 * @author 赵生辉 2017年06月30日 17:11
 *
 * @version 1.0.0
 */
@Data
@ToString
public class PartInfoDto implements Serializable
{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "图片")
    private List<Image> images;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "业务主键")
    private String uuid;

    @ApiModelProperty(value = "编号")
    private String sn;
}
