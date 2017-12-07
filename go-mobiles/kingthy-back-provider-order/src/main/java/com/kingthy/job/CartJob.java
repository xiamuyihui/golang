package com.kingthy.job;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 购物车相关的定时任务
 *
 * CartJob
 * 
 * @author 潘勇 2017年2月13日 下午4:57:21
 * 
 * @version 1.0.0
 *
 */
@Lazy(false)
@Component("cartJob")
public class CartJob
{
    
    // @Resource(name = "cartServiceImpl")
    // private CartService cartService;
    //
    // /**
    // * 清除过期购物车
    // */
    // @Scheduled(cron = "${job.cart_evict_expired.cron}")
    // public void evictExpired()
    // {
    // cartService.evictExpired();
    // }
    
}