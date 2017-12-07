package com.kingthy.service;

import com.dangdang.ddframe.job.api.ShardingContext;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 15:55 on 2017/9/26.
 * @Modified by:
 */
public interface TaskIfiService
{
    /**
     * 同步颜色数据
     * @param pageNum
     * @param pageSize
     * @param shardingContext 分片信息
     * @throws Exception
     */
    void executeColourIfi(Integer pageNum, Integer pageSize,ShardingContext shardingContext) throws Exception;

    /**
     * 部件类别
     * @param pageNum
     * @param pageSize
     * @param shardingContext
     * @throws Exception
     */
    void executeComponentTypeIfi(Integer pageNum, Integer pageSize,ShardingContext shardingContext) throws Exception;

    /**
     * 零件类别
     * @param pageNum
     * @param pageSize
     * @param shardingContext
     * @throws Exception
     */
    void executePartsTypeIfi(Integer pageNum, Integer pageSize,ShardingContext shardingContext) throws Exception;

    /**
     * 款式
     * @param pageNum
     * @param pageSize
     * @param shardingContext
     * @throws Exception
     */
    void executeStyleTypeIfi(Integer pageNum, Integer pageSize,ShardingContext shardingContext) throws Exception;

    /**
     * 计量单位
     * @param pageNum
     * @param pageSize
     * @param shardingContext
     * @throws Exception
     */
    void executeIfiMeasureUnit(Integer pageNum, Integer pageSize,ShardingContext shardingContext) throws Exception;
}
