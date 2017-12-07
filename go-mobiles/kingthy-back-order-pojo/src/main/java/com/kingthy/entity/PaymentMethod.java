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
public class PaymentMethod extends BaseTableFileds
{
    
    private Integer orders;
    
    private String description;
    
    private String icon;
    
    private Integer method;
    
    private String name;
    
    private Integer timeout;
    
    private Integer type;
    
    private String content;
    
    private static final long serialVersionUID = 1L;
}