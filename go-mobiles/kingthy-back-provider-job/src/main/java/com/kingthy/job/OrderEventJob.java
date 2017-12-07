package com.kingthy.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.kingthy.dubbo.order.service.EventPublishDubboService;


/**
 * @author xumin
 * @Description:
 * @DATE Created by 10:32 on 2017/8/22.
 * @Modified by:
 */
public class OrderEventJob implements SimpleJob{

    @Reference(version = "1.0.0")
    private EventPublishDubboService eventPublishDubboService;

    @Override
    public void execute(ShardingContext shardingContext) {

        //暂时不分片处理
//        if (shardingContext.getShardingItem() == 0){
            eventPublishDubboService.pushEventBySharding(shardingContext.getShardingParameter(), 500);
//        }

    }
}
