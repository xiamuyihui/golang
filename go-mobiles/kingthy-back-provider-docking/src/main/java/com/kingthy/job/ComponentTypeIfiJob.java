package com.kingthy.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.kingthy.service.TaskIfiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xumin
 * @Description: 部件类别
 * @DATE Created by 16:44 on 2017/9/12.
 * @Modified by:
 */
public class ComponentTypeIfiJob implements SimpleJob
{

    private static final Logger LOG = LoggerFactory.getLogger(ComponentTypeIfiJob.class);

    @Autowired
    private TaskIfiService taskIfiService;

    @Override
    public void execute(ShardingContext shardingContext) {

        try {

            taskIfiService.executeComponentTypeIfi(0, 100, shardingContext);

        } catch (Exception e) {
            LOG.error("同步部件类别表[parts_category]失败" + e);
        }

    }

}
