package com.kingthy.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

import com.kingthy.common.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author:xumin
 * @Description:
 * @Date:17:44 2017/11/2
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class Cart extends BaseEntity implements Serializable
{
    
    /** 超时时间 */
    public static final int TIMEOUT = 604800;
    
    /** 最大购物车项数量 */
    public static final Integer MAX_CART_ITEM_COUNT = 100;
    
    private Date expire;
    
    private String memberUuid;
    
    private Boolean delFlag;
    
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY, generator = "SELECT uuid_short()")
    private String uuid;
    
    @Transient
    private java.util.List<CartItem> cartItems;
    
    private static final long serialVersionUID = 1L;
    
}