package com.kingthy.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.kingthy.service.TaskIfiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xumin
 * @Description: 零件类别
 * @DATE Created by 15:59 on 2017/9/13.
 * @Modified by:
 */
public class PartsTypeIfiJob implements SimpleJob
{

    private static final Logger LOG = LoggerFactory.getLogger(StyleTypeIfiJob.class);

    @Autowired
    private TaskIfiService taskIfiService;

    @Override
    public void execute(ShardingContext shardingContext) {

        try {

            taskIfiService.executePartsTypeIfi(0, 100, shardingContext);

        } catch (Exception e) {
            LOG.error("同步零件表[spare_part]失败" + e);
        }
    }

}
