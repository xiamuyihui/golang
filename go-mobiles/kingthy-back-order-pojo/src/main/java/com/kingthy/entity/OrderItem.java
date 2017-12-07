package com.kingthy.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @author:xumin
 * @Description:
 * @Date:18:04 2017/11/2
 */
@Data
@ToString
public class OrderItem implements Serializable {
    private Long id;

    private Date createDate;

    private Date modifyDate;

    private Long version;

    private Boolean isDelivery;

    private String name;

    private BigDecimal price;

    private Integer quantity;

    private Date expire;

    private String memo;

    private Integer paymentMethodType;

    private String phone;

    private BigDecimal refundAmount;

    private Integer returnedQuantity;

    private Integer shippedQuantity;

    /**
     * 配送方式业务名称
     */
    private String shippingMethodName;

    private String shippingNumber;

    private String memberName;

    private String memberUuid;

    /**
     * 配送方式业务主键
     */
    private String paymentMethodUuid;

    private String shippingMethodUuid;

    private Integer status;

    private String sn;

    private String thumbnail;

    private Integer type;

    private String orderUuid;

    private String productUuid;

    private String specifications;


    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY, generator = "SELECT uuid_short()")
    private String uuid;

    private Boolean delFlag;

    private String creator;

    private String modifier;

    private String figureUuid;

    /**
     * 发票抬头
     */
    private String invoiceTitle;

    /**
     * 是否需要发票
     */
    private Boolean isInvoice;

    /**
     * 发票类型 0: 个人 1公司
     */
    private Integer invoiceType;

    /**
     * 订单总金额
     */
    private BigDecimal amount;

    private static final long serialVersionUID = 1L;

}