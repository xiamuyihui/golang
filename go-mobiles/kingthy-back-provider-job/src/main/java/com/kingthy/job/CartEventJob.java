package com.kingthy.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.kingthy.dubbo.cart.service.EventProcessDubboService;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 16:42 on 2017/8/23.
 * @Modified by:
 */
public class CartEventJob implements SimpleJob
{

    @Reference(version = "1.0.0")
    private EventProcessDubboService eventProcessDubboService;

    @Override
    public void execute(ShardingContext shardingContext) {

        //暂时不分片处理
//        if (shardingContext.getShardingItem() == 0){
            eventProcessDubboService.updateCartByEvent(shardingContext.getShardingParameter(), 200);
//        }
    }
}
