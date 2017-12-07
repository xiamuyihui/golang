package com.kingthy.job;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.kingthy.service.OrderService;

/**
 * 订单相关的定时任务
 *
 * OrderJob
 * 
 * @author 潘勇 2017年2月13日 下午4:57:05
 * 
 * @version 1.0.0
 *
 */
@Lazy(false)
@Component("orderJob")
public class OrderJob
{
    
    @Resource(name = "orderServiceImpl")
    private OrderService orderService;
    
    /**
     * 过期订单处理
     */
    public void expiredProcessing()
    {
    }
    
}