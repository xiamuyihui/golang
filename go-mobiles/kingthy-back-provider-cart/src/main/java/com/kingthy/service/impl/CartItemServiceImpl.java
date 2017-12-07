package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kingthy.conf.RedisManager;
import com.kingthy.dubbo.cart.service.CartItemDubboService;
import com.kingthy.dubbo.goods.service.GoodsDubboService;
import com.kingthy.entity.CartItem;
import com.kingthy.entity.Goods;
import com.kingthy.exception.BizException;
import com.kingthy.exception.bizexcapiton.CartBizException;
import com.kingthy.response.CartResp;
import com.kingthy.service.CartItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author shenghuizhao
 */
@Service("cartItemService")
public class CartItemServiceImpl implements CartItemService
{
    @Reference(version = "1.0.0")
    private  CartItemDubboService cartItemDubboService;

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CartServiceImpl cartServiceImpl;

    private static final Logger LOG= LoggerFactory.getLogger(CartItemServiceImpl.class);


    //@HystrixCommand(fallbackMethod = "updateCartItemFallback")
    @Override
    public int updateCartItem(CartItem cartItem, int quantity,String memberUuid)
        throws CartBizException
    {
        String cartCacheKey = redisManager.generateCacheKey(CartResp.class,memberUuid);
        redisManager.delete(cartCacheKey);
        String cacheKey = redisManager.generateCacheKey(CartItem.class,cartItem.getUuid());
        redisManager.delete(cacheKey);
        int result = cartItemDubboService.updateCartItem( cartItem, quantity);
        cartServiceImpl.list(memberUuid);
       return result;
    }
    private int updateCartItemFallback(CartItem cartItem, int quantity,String memberUuid,Throwable e){
        LOG.error("updateCartItem 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }

    //@HystrixCommand(fallbackMethod = "findCartItemFallback")
    @Override
    public CartItem findCartItem(String cartItemUuid)
    {
        CartItem cartItem = new CartItem();
        String cacheKey = redisManager.generateCacheKey(CartItem.class,cartItemUuid);
        String cacheData = redisManager.get(cacheKey);
        if(!StringUtils.isEmpty(cacheData))
        {
            cartItem = JSONObject.parseObject(cacheData,CartItem.class);
        }
        else
        {
            cartItem = cartItemDubboService.findCartItem(cartItemUuid);
            long expire = redisManager.getExpire(cacheKey);
            if (expire <= 0)
            {

                redisManager.set(cacheKey,JSONObject.toJSONString(cartItem),redisManager.getRandomExpire(), TimeUnit.DAYS);
            }
        }
        return cartItem;
    }

    @Override
    public Goods findGoodsByUuid(String goodsUuid) {

        return goodsDubboService.selectByUuid(goodsUuid);
    }

    private CartItem findCartItemFallback(String cartItemUuid,Throwable e)  {
        LOG.error("findCartItem 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }

    @Reference(version = "1.0.0",check = false)
    private GoodsDubboService goodsDubboService;
}
