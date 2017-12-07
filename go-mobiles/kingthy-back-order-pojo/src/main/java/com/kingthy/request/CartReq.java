package com.kingthy.request;

import java.io.Serializable;
import java.util.Set;

import lombok.Data;
import lombok.ToString;

/**
 * @author:xumin
 * @Description:
 * @Date:17:45 2017/11/2
 */
@Data
@ToString
public class CartReq implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private String token;
    
    private Set<CartItemReq> cartItemReqs;
}
