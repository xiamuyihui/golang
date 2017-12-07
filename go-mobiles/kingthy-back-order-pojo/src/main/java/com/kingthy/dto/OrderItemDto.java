package com.kingthy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 17:46 on 2017/7/17.
 * @Modified by:
 */
@Data
@ToString
public class OrderItemDto implements Serializable {

    private String uuid;
    private String sn;
    private Double amount;
    private String memberName;
    private Integer paymentMethodType;
    private String orderAddress;
    private String shippingMethodName;
    private Boolean isInvoice;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date paymentDate;

    @Data
    @ToString
    public static class OrderDetail implements Serializable{

        private String orderSn;
        private Integer status;
        private String memberName;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
        private Date createDate;
        private Integer paymentMethodType;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
        private Date paymentDate;
        private String shippingMethodName;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
        private Date deliveryDate;
        private String shippingNumber;
        private String consignee;
        private String phone;
        private String areaName;
        private String address;
    }

    @Data
    @ToString
    public static class InvoiceInfo extends OrderDetail implements Serializable{

        @ApiModelProperty("是否需要发票")
        private Boolean isInvoice;

        @ApiModelProperty("发票抬头")
        private String invoiceTitle;

        private String memo;

        private String goodsName;

        private Double price;

        private Integer quantity;

        private Double amount;

        private Integer paymentMethodType;

        @ApiModelProperty("体型数据")
        private String figureName;

        @JsonIgnore
        private String figureUuid;

        private String desinger;

        @ApiModelProperty("商品ID")
        private String productUuid;

    }
}
