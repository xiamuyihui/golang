package com.kingthy.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.kingthy.service.TaskIfiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author:xumin
 * @Description: 同步颜色数据
 * @Date:14:08 2017/11/1
 */
public class ColourIfiJob implements SimpleJob
{

    private static final Logger LOG = LoggerFactory.getLogger(ColourIfiJob.class);

    @Autowired
    private TaskIfiService taskIfiService;

    @Override
    public void execute(ShardingContext shardingContext)
    {

        try {

            taskIfiService.executeColourIfi(0, 100, shardingContext);

        } catch (Exception e) {
            LOG.error("同步颜色表[base_colour_info]失败" + e);
        }

    }


}
