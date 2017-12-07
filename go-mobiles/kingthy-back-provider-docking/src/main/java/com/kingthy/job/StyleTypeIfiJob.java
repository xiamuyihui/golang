package com.kingthy.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.kingthy.service.TaskIfiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xumin
 * @Description: 款式定时器
 * @DATE Created by 18:26 on 2017/9/11.
 * @Modified by:
 */
public class StyleTypeIfiJob implements SimpleJob
{
    private static final Logger LOG = LoggerFactory.getLogger(StyleTypeIfiJob.class);


    @Autowired
    private TaskIfiService taskIfiService;

    @Override
    public void execute(ShardingContext shardingContext) {

        try {

            taskIfiService.executeStyleTypeIfi(0, 100, shardingContext);

        } catch (Exception e) {
            LOG.error("同步品类表[class_category]失败" + e);
        }
    }


}
