package com.kingthy.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 11:00 on 2017/7/17.
 * @Modified by:
 */
@Data
@ToString
public class MessagePlatformDto implements java.io.Serializable{

    @ApiModelProperty("编号")
    private String uuid;

    @ApiModelProperty("平台名称")
    private String platformName;

    @ApiModelProperty("平台描述")
    private String platformDesc;

    @ApiModelProperty("开通时间")
    private Date openTime;

    @ApiModelProperty("状态 0启用1禁用")
    private Long status;
}
