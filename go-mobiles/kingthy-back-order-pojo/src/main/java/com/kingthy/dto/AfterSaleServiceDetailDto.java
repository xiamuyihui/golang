package com.kingthy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 10:28 on 2017/7/11.
 * @Modified by:
 */
@Data
@ToString
public class AfterSaleServiceDetailDto implements java.io.Serializable{


    @ApiModelProperty("订单号")
    private String orderSn;
    @ApiModelProperty("0:发起售后 1:审核通过 2:厂家确认收货,待生产 3:生产中 4:已包装发货，等待收货 5:已收货 6:已评价/完成售后")
    private Integer status;
    @ApiModelProperty("商品名称")
    private String goodsName;

    @JsonIgnore
    private String goodsUuid;

    @ApiModelProperty("订单金额")
    private BigDecimal amount;
    @ApiModelProperty("数量")
    private Integer quantity;
    @ApiModelProperty("用户")
    private String memberName;
    @ApiModelProperty("商品单价")
    private BigDecimal price;
    @ApiModelProperty("下单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date orderCreateDate;
    @ApiModelProperty("付款时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date paymentDate;
    @ApiModelProperty("申请服务名称")
    private String applyServiceName;
    @ApiModelProperty("换货原因")
    private String exchangeReason;
    @ApiModelProperty("上传凭证")
    private String img;
    private String uuid;
    private String memo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date applyCreateDate;
    private String cover;

    @ApiModelProperty("是否完成生产")
    private Boolean producingFlag;

}
