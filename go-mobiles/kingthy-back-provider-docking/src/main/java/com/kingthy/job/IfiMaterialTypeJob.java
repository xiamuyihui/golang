package com.kingthy.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.kingthy.service.IfiMaterialTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xumin
 * @Description:同步物料分类
 * @DATE Created by 16:34 on 2017/9/22.
 * @Modified by:
 */
public class IfiMaterialTypeJob implements SimpleJob
{

    private static final Logger LOG = LoggerFactory.getLogger(IfiMaterialTypeJob.class);

    @Autowired
    private IfiMaterialTypeService ifiMaterialTypeService;

    @Override
    public void execute(ShardingContext shardingContext)
    {

        try
        {

            ifiMaterialTypeService.executeSyn(0, 100, shardingContext);

        }
        catch (Exception e)
        {
            LOG.error("同步物料分类表[materiel_category]失败" + e);
        }
    }
}
