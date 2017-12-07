package com.kingthy.request;

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
public class CartItemReq implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private Integer quantity;
    
    private String cartUuid;
    
    private String goodsUuid;
}
