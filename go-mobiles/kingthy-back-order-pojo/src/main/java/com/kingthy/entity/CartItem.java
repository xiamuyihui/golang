package com.kingthy.entity;

import java.io.Serializable;

import com.kingthy.common.BaseEntity;

import lombok.EqualsAndHashCode;

/**
 * @author:xumin
 * @Description:
 * @Date:17:44 2017/11/2
 */
@lombok.Data
@lombok.ToString
@EqualsAndHashCode(callSuper = true)
public class CartItem extends BaseEntity implements Serializable
{
    
    /** 最大数量 */
    public static final Integer MAX_QUANTITY = 100;
    
    private static final long serialVersionUID = 1L;
    
    private Integer quantity;
    
    private String cartUuid;
    
    private String goodsUuid;
    
    private Boolean delFlag;
    
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY, generator = "SELECT uuid_short()")
    private String uuid;
}