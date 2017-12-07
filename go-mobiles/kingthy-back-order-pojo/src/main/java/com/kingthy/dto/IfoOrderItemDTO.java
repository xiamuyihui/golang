package com.kingthy.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kingthy.common.BaseReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 18:30 on 2017/9/25.
 * @Modified by:
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class IfoOrderItemDTO extends BaseReq implements Serializable {

    @JsonIgnore
    @ApiModelProperty("订单号")
    private String orderSn;

    @ApiModelProperty("子订单号")
    private String orderItemSn;

    @ApiModelProperty("款式编码")
    private String styleCode;

    @ApiModelProperty("款式名称")
    private String styleName;

    @ApiModelProperty("款式分类编码")
    private String styleTypeCode;

    @ApiModelProperty("款式分类名称")
    private String styleTypeName;

    @ApiModelProperty("特殊工艺标识 (Y有特殊工艺，N没有特殊工艺)")
    private Boolean stFlag;

    @ApiModelProperty("特殊工艺花样编码 多个以逗号隔开")
    private String stCodes;

    @ApiModelProperty("实样文件地址(类似plt文件，这里是在客户端上的文件全路径）")
    private String syFilePath;

    @ApiModelProperty("主设计文件路径 传款式对应的图片地址")
    private List<String> filePath;

    @ApiModelProperty("价格")
    private Double price;
}
