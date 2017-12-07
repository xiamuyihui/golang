package com.kingthy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.dubbo.cart.service.CartDubboService;
import com.kingthy.dubbo.goods.service.GoodsDubboService;
import com.kingthy.entity.Cart;
import com.kingthy.entity.CartItem;
import com.kingthy.entity.Goods;
import com.kingthy.exception.bizexcapiton.CartBizException;
import com.kingthy.response.CartResp;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.CartItemService;
import com.kingthy.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车
 *
 * CartController
 * 
 * @author 潘勇 2017年3月3日 上午10:04:27
 * 
 * @version 1.0.0
 *
 */
@Api
@RestController
@RequestMapping("/cart")
public class CartController
{
    private static final Logger LOG = LoggerFactory.getLogger(CartController.class);
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    protected String getMemberByToken(String token)
    {
        String memberUuid = stringRedisTemplate.opsForValue().get(token);
        return memberUuid;
    }

    /**
     * 数量
     */
    @ApiOperation(value = "计算数量接口暂未实现", notes = "")
    @RequestMapping(value = "/quantity", method = RequestMethod.GET)
    public WebApiResponse<?> quantity()
    {
        return null;
    }
    
    /**
     * 添加至购物车
     * 
     * @return
     * @author 潘勇
     * @return WebApiResponse<?>
     * @begin 2017-03-03 10:04
     * @since 1.0.0
     */
    @ApiOperation(value = "购物车添加接口", notes = "")
    @PostMapping(value = "/add")
    public WebApiResponse<?> add(
        @RequestBody @ApiParam(name = "cartParam", value = "购物车对象", required = true) Cart cartParam,
        @RequestHeader("Authorization") String token)
    {
        try {
            if (StringUtils.isEmpty(token)){
                return WebApiResponse.error("token不能为空");
            }

            if (cartParam.getCartItems().size() == 0)
            {
                return WebApiResponse.error("购物车不能为空");
            }
            int quantity = cartParam.getCartItems().get(0).getQuantity();
            if (quantity < 1)
            {
                return WebApiResponse.error("购物车数量不能为空");
            }
            String goodsUuid = cartParam.getCartItems().get(0).getGoodsUuid();
            if (null == goodsUuid)
            {
                return WebApiResponse.error("商品ID不能为空");
            }

            Goods goods = cartItemService.findGoodsByUuid(goodsUuid);

            if (goods == null)
            {
                return WebApiResponse.error("商品不存在");
            }
            else if (goods.getStatus() != Goods.GoodsStatus.goodsUp.getValue())
            {
                return WebApiResponse.error("商品已下架");
            }


            String memberUuid = getMemberByToken(token);

            if (StringUtils.isEmpty(memberUuid)){
                return WebApiResponse.error("token不正确");
            }


            Cart cart = cartService.getCurrentCart(memberUuid);
            if (cart != null)
            {
                if (cartService.contains(cart, goods))
                {
                    CartItem cartItem = cartService.getCartItem(cart, goods);
                    if (CartItem.MAX_QUANTITY != null && cartItem.getQuantity() + quantity > CartItem.MAX_QUANTITY)
                    {
                        return WebApiResponse.error("购物车数量超过最大限制:" + CartItem.MAX_QUANTITY);
                    }
                }
                else
                {
                    if (Cart.MAX_CART_ITEM_COUNT != null
                            && cartService.getCartItemById(cart.getUuid()).size() >= Cart.MAX_CART_ITEM_COUNT)
                    {
                        return WebApiResponse.error("最多允许添加" + Cart.MAX_CART_ITEM_COUNT + "种商品至购物车:");
                    }
                    if (CartItem.MAX_QUANTITY != null && quantity > CartItem.MAX_QUANTITY)
                    {
                        return WebApiResponse.error("商品数量不允许超过:" + CartItem.MAX_QUANTITY);
                    }
                }
            }
            else
            {
                if (CartItem.MAX_QUANTITY != null && quantity > CartItem.MAX_QUANTITY)
                {
                    return WebApiResponse.error("商品数量不允许超过:" + CartItem.MAX_QUANTITY);
                }
            }

            cart = cartService.add(goods, quantity, memberUuid);
            return WebApiResponse.success(cart);
        }catch (Exception e){
            e.printStackTrace();
            LOG.error("添加至购物车"+e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }

    }


    
    /**
     * 编辑购物车
     * 
     * @return
     * @author 潘勇
     * @return WebApiResponse<?>
     * @begin 2017-03-03 10:05
     * @since 1.0.0
     */
    @ApiOperation(value = "购物车编辑接口", notes = "")
    @RequestMapping(value = "/edit/{cartItemUuid}/{quantity}", method = RequestMethod.PUT)
    public @ResponseBody WebApiResponse<?> edit(
        @PathVariable @ApiParam(name = "cartItemUuid", value = "购物车子项业务主键", required = true) String cartItemUuid,
        @PathVariable @ApiParam(name = "quantity", value = "数量", required = true) Integer quantity,
        @RequestHeader("Authorization") String token)
    {
        String memberUuid = getMemberByToken(token);
        if (cartService.checkCartItem(memberUuid, cartItemUuid) == 0)
        {
            return WebApiResponse.error("该用户无权修改此购物车");
        }
        if(StringUtils.isBlank(memberUuid)){
            return WebApiResponse.error("不是有效用户");
        }
        if (quantity == null || quantity < 1)
        {
            return WebApiResponse.error("购物车数量必须大于零");
        }
        Cart cart = cartService.getCurrentCart(memberUuid);
        if (cart == null)
        {
            return WebApiResponse.error("购物车不能为空");
        }
        CartItem cartItem = cartItemService.findCartItem(cartItemUuid);

        if (cartItem == null){
            return WebApiResponse.error("购物车不存在");
        }

        if (CartItem.MAX_QUANTITY != null && quantity > CartItem.MAX_QUANTITY)
        {
            return WebApiResponse.error("超出了购物车的最大数量");
        }
        cartItem.setQuantity(quantity);
        try
        {
            cartItemService.updateCartItem(cartItem, quantity,memberUuid);
            return WebApiResponse.success();
        }
        catch (CartBizException e)
        {
            LOG.error("更新购物车数量异常:" + e);
        }
        return WebApiResponse.error("购物车更新失败");
    }
    
    @ApiOperation(value = "购物车列表接口", notes = "")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public WebApiResponse<?> list(@RequestHeader("Authorization") String token)
    {

        String memberUuid = getMemberByToken(token);
        if (StringUtils.isBlank(memberUuid))
        {
            return WebApiResponse.error("无法获取用户信息");
        }
        List<CartResp> cartResps = null;
        try
        {
            cartResps = cartService.list(memberUuid);
        }
        catch (Exception e)
        {
            LOG.error("/cart/list/:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
        return WebApiResponse.success(cartResps);
        
    }
    
    /**
     * 删除购物车中商品
     * 
     * @return
     * @author 潘勇
     * @return WebApiResponse<?>
     * @begin 2017-03-03 10:05
     * @since 1.0.0
     */
    @ApiOperation(value = "购物车删除接口", notes = "")
    @RequestMapping(value = "/delete/{cartItemUuid}", method = RequestMethod.DELETE)
    public WebApiResponse<?> delete(
        //@PathVariable @ApiParam(name = "token", value = "登录令牌", required = true) String token,
        @RequestHeader("Authorization") String token,
        @PathVariable @ApiParam(name = "cartItemUuid", value = "登录令牌", required = true) String cartItemUuid)
    {
        String memberUuid = getMemberByToken(token);
        if (StringUtils.isBlank(memberUuid))
        {
            return WebApiResponse.error("无法获取用户信息");
        }
        if (cartService.checkCartItem(memberUuid, cartItemUuid) == 0)
        {
            return WebApiResponse.error("无数据可删除");
        }
        else
        {
            int reslut = 0;
            try
            {
                reslut = cartService.remove(cartItemUuid,memberUuid);
            }
            catch (Exception e)
            {
                LOG.error("/cart/delete/:" + e);
                return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
            }
            return reslut >= 1 ? WebApiResponse.success("删除购物车数据成功") : WebApiResponse.success("删除购物车数据失败");
        }
    }
    
    /**
     * 清空购物车
     * 
     * @return
     * @author 潘勇
     * @return WebApiResponse<?>
     * @begin 2017-03-03 10:05
     * @since 1.0.0
     */
    @ApiOperation(value = "购物车清空接口", notes = "")
    @RequestMapping(value = "/clear", method = RequestMethod.PUT)
    public @ResponseBody WebApiResponse<?> clear(
        @RequestHeader("Authorization") String token
    )
    {
        String memberUuid = getMemberByToken(token);
        if (StringUtils.isBlank(memberUuid))
        {
            return WebApiResponse.error("无法获取用户信息");
        }
        try
        {
            Cart cart = cartService.getCurrentCart(memberUuid);
            cartService.removeAllCart(cart);
        }
        catch (Exception e)
        {
            LOG.error("/cart/clear/:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
        return WebApiResponse.success("清空购物车数据成功");
    }
    
}