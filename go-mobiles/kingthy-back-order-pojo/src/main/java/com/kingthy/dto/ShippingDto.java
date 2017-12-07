package com.kingthy.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 16:00 on 2017/7/12.
 * @Modified by:
 */

@Data
@ToString
public class ShippingDto implements java.io.Serializable{

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
    @ApiModelProperty("用户")
    private String memberName;
    @ApiModelProperty("商品单价")
    private BigDecimal price;
    @ApiModelProperty("运费")
    private BigDecimal freight;
    @ApiModelProperty("下单时间")
    private Date orderCreateDate;
    @ApiModelProperty("付款时间")
    private Date paymentDate;
    @ApiModelProperty("收货地址")
    private String address;
    @ApiModelProperty("是否需要发票")
    private boolean isInvoice;
    private String uuid;
}
