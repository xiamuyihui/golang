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
public class PaymentLog extends BaseTableFileds
{
    
    private BigDecimal amount;
    
    private BigDecimal fee;
    
    private String paymentPluginId;
    
    private String paymentPluginName;
    
    private String sn;
    
    private Integer status;
    
    private Integer type;
    
    private String memberUuid;
    
    private String ordersUuid;
    
    private static final long serialVersionUID = 1L;
    
}