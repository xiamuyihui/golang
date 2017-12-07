package com.kingthy.dto;

import com.kingthy.common.BaseReq;

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
public class CartDTO extends BaseReq
{
    private static final long serialVersionUID = -2451779689307432664L;
     
    private Long id;
    
    private String memberId;
    
    private java.util.Set<CartItemDTO> cartItems;
}
