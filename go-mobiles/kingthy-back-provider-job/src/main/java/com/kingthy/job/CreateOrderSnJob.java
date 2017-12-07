package com.kingthy.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.kingthy.common.CommonUtils;
import com.kingthy.service.CreateSnService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 10:41 on 2017/11/21.
 * @Modified by:
 */
public class CreateOrderSnJob implements SimpleJob
{

    @Autowired
    private CreateSnService createSnService;

    @Override
    public void execute(ShardingContext shardingContext) {

        if (shardingContext.getShardingItem() == 0){

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

            String key = CommonUtils.REIDS_ORDER_SN_KEY + ":" + sdf.format(new Date());

            createSnService.saveSnToRedis(key, "order");
        }
    }
}
