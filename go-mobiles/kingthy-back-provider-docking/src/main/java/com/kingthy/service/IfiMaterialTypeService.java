package com.kingthy.service;

import com.dangdang.ddframe.job.api.ShardingContext;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 16:35 on 2017/9/22.
 * @Modified by:
 */
public interface IfiMaterialTypeService {

    /**
     * 同步数据
     * @param pageNum
     * @param pageSize
     * @param shardingContext 分片信息
     * @throws Exception
     */
    void executeSyn(Integer pageNum, Integer pageSize,ShardingContext shardingContext) throws Exception;
}
