package com.kingthy.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 11:06 on 2017/5/3.
 * @Modified by:
 */
@Data
@ToString
public class CityInfoDTO  implements Serializable {

    private static final long serialVersionUID = 726080152327286989L;

    @ApiModelProperty("县区ID")
    private Integer areaUuid;
    @ApiModelProperty("县区名称")
    private String areaName;

    @ApiModelProperty("城市ID")
    private Integer cityUuid;
    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty("省ID")
    private Integer provinceUuid;
    @ApiModelProperty("省名称")
    private String provinceName;

}
