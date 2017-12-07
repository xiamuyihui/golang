package com.kingthy.entity;

import java.math.BigDecimal;

import com.kingthy.common.BaseTableFileds;

import lombok.Data;
import lombok.ToString;
/**
 * @author:xumin
 * @Description:
 * @Date:18:04 2017/11/2
 */
@Data
@ToString
public class Payment extends BaseTableFileds
{
    private String account;
    
    private BigDecimal amount;
    
    private String bank;
    
    private BigDecimal fee;
    
    private String memo;
    
    private Integer method;
    
    private String operator;
    
    private String payer;
    
    private String paymentMethod;
    
    private String sn;
    
    private String orderUuid;
    
    private static final long serialVersionUID = 1L;
    
}