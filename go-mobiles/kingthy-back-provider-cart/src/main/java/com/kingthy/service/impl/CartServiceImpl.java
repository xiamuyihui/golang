package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingthy.conf.RedisManager;
import com.kingthy.dto.CartDTO;
import com.kingthy.dubbo.cart.service.CartDubboService;
import com.kingthy.dubbo.cart.service.CartItemDubboService;
import com.kingthy.dubbo.goods.service.GoodsDubboService;
import com.kingthy.entity.Cart;
import com.kingthy.entity.CartItem;
import com.kingthy.entity.Goods;
import com.kingthy.exception.BizException;
import com.kingthy.response.CartResp;
import com.kingthy.service.CartService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author shenghuizhao
 */
@Service(value = "cartService")
public class CartServiceImpl implements CartService
{

    @Reference(version = "1.0.0",check = false)
    private transient CartDubboService cartDubboService;

    @Reference(version = "1.0.0",check = false)
    private transient CartItemDubboService cartItemDubboService;

    @Reference(version = "1.0.0",check = false)
    private transient GoodsDubboService goodsDubboService;
    
    @Autowired
    private transient StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    private static final Logger LOG = LoggerFactory.getLogger(CartServiceImpl.class);
    
    /**
     * 根据token获取当前用户的购物车信息
     * 
     * @return
     * @author 潘勇
     * @return Cart
     * @begin 2017-03-08 15:03
     * @since 1.0.0
     */
    //@HystrixCommand(fallbackMethod = "getCurrentCartFallback")
    @Override
    public Cart getCurrentCart(String memberUuid)
    {
        Cart cart = null;
        try
        {
            cart = cartDubboService.getCurrentCart(memberUuid);
        }
        catch (Exception e)
        {
            throw new BizException("123","321");
        }
        return cart;
    }
    private Cart getCurrentCartFallback(String memberUuid,Throwable e){
        LOG.error("getCurrentCart 发生异常，进入熔断，异常信息：" + e.getMessage());
        if(!"".equals(e.getMessage()))
        {
            throw new BizException("123","321");
        }
        return null;
    }
    /**
     * 添加商品以及数量到用户的购物车中
     * 
     * @param goods
     * @param quantity
     * @param memberUuid
     * @return
     * @author 潘勇
     * @return Cart
     * @begin 2017-03-08 15:04
     * @since 1.0.0
     */
    @Override
    @HystrixCommand(fallbackMethod = "addFallback")
    public Cart add(Goods goods, int quantity, String memberUuid)
    {
        String cacheKey = redisManager.generateCacheKey(CartResp.class,memberUuid);
        long expire = redisManager.getExpire(cacheKey);
        if (expire > 0)
        {
            redisManager.delete(cacheKey);
            list(memberUuid);
        }
        return cartDubboService.add(goods,quantity,memberUuid);
    }
    private Cart addFallback(Goods goods, int quantity, String memberUuid,Throwable e){
        LOG.error("add 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }
    @Override
    @HystrixCommand(fallbackMethod = "getCartItemFallback")
    public CartItem getCartItem(Cart cart, Goods goods)
    {
        for (CartItem cartItem : getCartItemById(cart.getUuid()))
        {
            if (cartItem.getGoodsUuid().equals(goods.getUuid()))
            {
                return cartItem;
            }
        }
        return null;
    }
    private CartItem getCartItemFallback(Cart cart, Goods goods,Throwable e){
        LOG.error("getCartItem 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }

    @Override
    @HystrixCommand(fallbackMethod = "getCartItemByIdFallback")
    public List<CartItem> getCartItemById(String cartUuid)
    {
        return cartDubboService.getCartItemById(cartUuid);
    }
    private List<CartItem> getCartItemByIdFallback(String cartUuid,Throwable e){
        LOG.error("getCartItemById 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }

    @Override
    @HystrixCommand(fallbackMethod = "containsFallback")
    public boolean contains(Cart cart, Goods goods)
    {
        return cartDubboService.contains(cart, goods);
    }
    private boolean containsFallback(Cart cart, Goods goods,Throwable e) {
        LOG.error("contains 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }

    @Override
    public Cart edit(CartDTO cart)
    {
        // TODO Auto-generated method stub
        return null;
    }


    //@HystrixCommand(fallbackMethod = "listFallback")
    @Override
    public List<CartResp> list(String memberUuid)
    {
        // TODO Auto-generated method stub
        List<CartResp> cartRespList = new ArrayList<>();
        String cacheKey = redisManager.generateCacheKey(CartResp.class,memberUuid);
        String cacheData = redisManager.get(cacheKey);
        if(!StringUtils.isEmpty(cacheData))
        {
            cartRespList = JSONObject.parseArray(cacheData,CartResp.class);
        }
        else
        {
            cartRespList = cartDubboService.list(memberUuid);

            if (cartRespList != null){

                List<CartResp> cartResps = new ArrayList<CartResp>();
                List<String> goodsUuids = new ArrayList<String>();
                cartRespList.forEach(cartResp -> {
                    goodsUuids.add(cartResp.getGoodsUuid());
                });
                List<Goods> goodsList = goodsDubboService.selectGoodsUuid(goodsUuids);
                for(CartResp car:cartRespList)
                {
                    goodsList.forEach(goods -> {
                        if(goods.getUuid().equals(car.getGoodsUuid()))
                        {
                            car.setGoodsFeature(goods.getGoodsFeature());
                            car.setGoodsImage(goods.getGoodsImage());
                            car.setDesinger(goods.getDesinger());
                            car.setGoodsName(goods.getGoodsName());

                            String status="已上架";
                            if(goods.getStatus()==null||goods.getStatus()==0){
                                status="已下架";
                            }
                            car.setGoodsStatus(status);
                            car.setStandardPrice(goods.getStandardPrice());
                            cartResps.add(car);
                        }
                    });
                }

            }
            long expire = redisManager.getExpire(cacheKey);
            if (expire <= 0)
            {
                redisManager.set(cacheKey, JSONArray.toJSONString(cartRespList),redisManager.getRandomExpire(), TimeUnit.DAYS);
            }
        }

        return cartRespList;
    }
    private List<CartResp> listFallback(String memberUuid,Throwable e){
        LOG.error("list 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }

    @HystrixCommand(fallbackMethod = "removeFallback")
    @Override
    public int remove(String cartItemUuid ,String memberUuid)
    {
        String cartCacheKey = redisManager.generateCacheKey(CartResp.class,memberUuid);
        redisManager.delete(cartCacheKey);
        String cacheKey = redisManager.generateCacheKey(CartItem.class,cartItemUuid);
        redisManager.delete(cacheKey);
        return cartDubboService.remove(cartItemUuid);
    }

    private int removeFallback(String cartItemUuid,String memberUuid,Throwable e){
        LOG.error("remove 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }
    @HystrixCommand(fallbackMethod = "checkCartItemFallback")
    @Override
    public int checkCartItem(String memberUuid, String cartItemUuid)
    {
        return cartDubboService.checkCartItem(memberUuid, cartItemUuid);
    }
    private  int checkCartItemFallback(String memberUuid, String cartItemUuid,Throwable e){
        LOG.error("checkCartItem 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }
    @HystrixCommand(fallbackMethod ="removeAllCartFallback")
    @Override
    public void removeAllCart(Cart cart)
    {
        cartDubboService.removeAllCart(cart);
    }
    private void removeAllCartFallback(Cart cart,Throwable e){
        LOG.error("checkCartItem 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }
}
