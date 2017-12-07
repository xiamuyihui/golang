package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 16:48 on 2017/7/10.
 * @Modified by:
 */
@Data
@ToString
public class InvoiceInfoDto implements Serializable {

    private String orderSn;
    private String memberUuid;
    private String phone;
    private String invoiceNo;
    private String invoiceTitle;
    private Double amount;
    private Integer invoiceType;
    private Date paymentDate;
    private Date billingTime;
    private Integer status;

}
