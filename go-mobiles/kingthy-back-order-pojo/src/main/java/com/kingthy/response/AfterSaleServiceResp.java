package com.kingthy.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 18:17 on 2017/6/8.
 * @Modified by:
 */
@Data
@ToString
public class AfterSaleServiceResp implements java.io.Serializable{

    @ApiModelProperty("订单号")
    private String orderSn;
    @ApiModelProperty("0:发起售后 1:审核通过 2:厂家确认收货,待生产 3:生产中 4:已包装发货，等待收货 5:已收货 6:已评价/完成售后")
    private Integer status;
    @ApiModelProperty("商品名称")
    private String goodsName;
    @ApiModelProperty("订单金额")
    private BigDecimal amount;
    @ApiModelProperty("数量")
    private Integer quantity;
    @ApiModelProperty("体型数据")
    private String figureName;
    @ApiModelProperty("商品单价")
    private BigDecimal price;
    @ApiModelProperty("封面图")
    private String cover;
    @JsonIgnore
    private String figureUuid;
    @JsonIgnore
    private String goodsUuid;
}
