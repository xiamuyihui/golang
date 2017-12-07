package com.kingthy.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kingthy.common.BaseReq;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
/**
 * @author:xumin
 * @Description:
 * @Date:18:04 2017/11/2
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class OrderDTO extends BaseReq implements Serializable
{
    private static final long serialVersionUID = 5019641384499008486L;

    @ApiModelProperty("支付方式类型 0:支付宝 1:微信 2:银联 3:QQ钱包 4:余额")
    private Integer paymentMethodType;

    @ApiModelProperty("收货地址")
    private String address;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("县区")
    private String areaName;

    @ApiModelProperty("县区Id")
    private Integer areaUuid;

    @ApiModelProperty("收货人")
    private String consignee;

    @ApiModelProperty("订单金额")
    private Double amount;

    @ApiModelProperty("商品信息")
    private List<OrderCreateDTO> goods;

    @ApiModelProperty("省份ID")
    @JsonIgnore
    private String provinceUuid;

    @ApiModelProperty("省份名称")
    @JsonIgnore
    private String provinceName;

    @ApiModelProperty("城市ID")
    @JsonIgnore
    private String cityUuid;

    @ApiModelProperty("城市名称")
    @JsonIgnore
    private String cityName;

    /**
     * 发票抬头
     */
    @ApiModelProperty("发票抬头")
    private String invoiceTitle;

    @ApiModelProperty("是否需要发票")
    private Boolean isInvoice;

    @ApiModelProperty("发票类型 0: 个人 1公司")
    private Integer invoiceType;

    @ApiModelProperty("配送方式名称")
    private String shippingMethodName;

    @ApiModelProperty("配送方式业务主键")
    private String shippingMethodUuid;

}
