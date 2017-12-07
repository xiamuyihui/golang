package com.kingthy.entity;

import lombok.Data;
import lombok.ToString;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @author:xumin
 * @Description:
 * @Date:18:14 2017/11/2
 */
@Data
@ToString
public class Shipping implements Serializable {
    private Long id;

    private Date createDate;

    private Date modifyDate;

    private Integer version;

    private String address;

    private String area;

    private String consignee;

    private String deliveryCorp;

    private String deliveryCorpCode;

    private String deliveryCorpUrl;

    private BigDecimal freight;

    private String memo;

    private String operator;

    private String phone;

    private String shippingMethod;

    private String sn;

    private String trackingNo;

    private String zipCode;

    private Long orderId;

    private String uuid;

    private String modifier;

    private String creator;

    private Boolean delFlag;
}