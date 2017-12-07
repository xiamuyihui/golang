package com.kingthy.service;

import com.kingthy.entity.CartItem;
import com.kingthy.entity.Goods;
import com.kingthy.exception.bizexcapiton.CartBizException;

/**
 * @author shenghuizhao
 */
public interface CartItemService
{
    /**
     * 更新购物车详情
     * @param cartItem
     * @param quantity
     * @param memberUuid
     * @return
     * @throws CartBizException
     */
    int updateCartItem(CartItem cartItem, int quantity,String memberUuid)
        throws CartBizException;

    /**
     * 查询购物车详情
     * @param cartItemUuid
     * @return
     * @throws CartBizException
     */
    CartItem findCartItem(String cartItemUuid)
        throws CartBizException;

    Goods findGoodsByUuid(String goodsUuid);
}
