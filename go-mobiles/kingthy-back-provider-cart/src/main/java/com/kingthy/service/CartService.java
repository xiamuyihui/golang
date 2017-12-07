package com.kingthy.service;

import java.util.List;

import com.kingthy.dto.CartDTO;
import com.kingthy.entity.Cart;
import com.kingthy.entity.CartItem;
import com.kingthy.entity.Goods;
import com.kingthy.response.CartResp;

/**
 * @author shenghuizhao
 */
public interface CartService
{

    /**
     * 添加商品方法
     * @param goods
     * @param quantity
     * @param memberUuid
     * @return
     */
    Cart add(Goods goods, int quantity, String memberUuid);

    /**
     * 修改购物车
     * @param cart
     * @return
     */
    Cart edit(CartDTO cart);

    /**
     * 查询购物车列表
     * @param memberId
     * @return
     */
    List<CartResp> list(String memberId);

    /**
     * 删除购物车
     * @param cartItemUuid
     * @param memberUuid
     * @return
     */
    int remove(String cartItemUuid ,String memberUuid);

    /**
     * 获取当前购物车
     * @param memberUuid
     * @return
     */
    Cart getCurrentCart(String memberUuid);

    /**
     * 验证购物车是否包含
     * @param cart
     * @param goods
     * @return
     */
    boolean contains(Cart cart, Goods goods);

    /**
     * 获取购物车详细信息
     * @param cart
     * @param goods
     * @return
     */
    CartItem getCartItem(Cart cart, Goods goods);

    /**
     * 通过购物车ID查询购物车列表
     * @param cartUuid
     * @return
     */
    List<CartItem> getCartItemById(String cartUuid);

    /**
     * 检查用户的购物车是否已拥有
     * @param memberUuid
     * @param cartItemUuid
     * @return
     */
    int checkCartItem(String memberUuid, String cartItemUuid);

    /**
     * 删除所有购物车
     * @param cart
     */
    void removeAllCart(Cart cart);
}
