package com.kingthy.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.kingthy.service.TaskIfiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xumin
 * @Description:计量单位
 * @DATE Created by 18:31 on 2017/9/26.
 * @Modified by:
 */
public class IfiMeasureUnitJob implements SimpleJob
{
    private static final Logger LOG = LoggerFactory.getLogger(IfiMeasureUnitJob.class);

    @Autowired
    private TaskIfiService taskIfiService;

    @Override
    public void execute(ShardingContext shardingContext) {

        try {

            taskIfiService.executeIfiMeasureUnit(0, 100, shardingContext);

        } catch (Exception e) {
            LOG.error("同步计量单位表[measure_unit]失败" + e);
        }
    }
}
