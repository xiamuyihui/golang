package com.kingthy.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

/**
 * @author:xumin
 * @Description:
 * @Date:17:44 2017/11/2
 */
@Data
@ToString
public class CartItemDTO implements Serializable
{
    private static final long serialVersionUID = 726080152027286989L;
    
    private Integer quantity;
    
    private String cartUuid;
    
    private String goodsUuid;
}
