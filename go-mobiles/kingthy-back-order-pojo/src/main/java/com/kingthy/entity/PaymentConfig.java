package com.kingthy.entity;

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
public class PaymentConfig extends BaseTableFileds
{
    
    private String name;
    
    private Integer type;
    
    private String mchId;
    
    private String appId;
    
    private String appKey;
    
    private String appSecret;
    
    private Boolean isUsed;
    
    private static final long serialVersionUID = 1L;
    
}