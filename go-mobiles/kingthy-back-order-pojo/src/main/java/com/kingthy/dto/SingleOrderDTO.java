package com.kingthy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by xumin on 2017/4/24.
 */
@Data
@ToString
public class SingleOrderDTO implements Serializable {

    @ApiModelProperty("收货地址")
    private String address;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("收货人")
    private String consignee;

    @ApiModelProperty("订单状态 0:代付款 1:生产中 2:待发货 3:待签收 4:待评价 5:已取消 6:待退单 7:已完成")
    private String status;

    @ApiModelProperty("订单号")
    private String sn;

    @ApiModelProperty("总金额")
    private BigDecimal amount;
    @ApiModelProperty("数量")
    private Integer quantity;
    @ApiModelProperty("名称")
    private String goodsName;
    @ApiModelProperty("图片")
    private String cover;
    @ApiModelProperty("面料")
    private String materielInfo;
    @ApiModelProperty("商品ID")
    private String goodsUuid;
    @ApiModelProperty("体型数据")
    private String figureName;

    @ApiModelProperty("支付方式类型 0:支付宝 1:微信 2:银联")
    private Integer paymentMethodType;

    @ApiModelProperty("配送方式业务名称")
    private String shippingMethodName;
    @ApiModelProperty("发票抬头")
    private String invoiceTitle;

    @ApiModelProperty("下单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date createDate;

    @ApiModelProperty("付款时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date paymentDate;

    @ApiModelProperty("是否需要发票")
    private Boolean isInvoice;

    @JsonIgnore
    private String figureUuid;
}
