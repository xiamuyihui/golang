package com.kingthy.conf;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.kingthy.job.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 15:30 on 2017/8/22.
 * @Modified by:
 */

@Configuration
public class DockingJobConfig {

    @Resource
    private ZookeeperRegistryCenter regCenter;

    /**
     * 颜色
     * @return
     */
    @Bean
    public ColourIfiJob colourIfiJob(){
        return new ColourIfiJob();
    }

    /**
     * 款式
     * @return
     */
    @Bean
    public StyleTypeIfiJob styleTypeIfiJob(){
        return new StyleTypeIfiJob();
    }

    /**
     * 部件
     * @return
     */
    @Bean
    public ComponentTypeIfiJob componentTypeIfiJob(){
        return new ComponentTypeIfiJob();
    }

    /**
     * 零件
     * @return
     */
    @Bean
    public PartsTypeIfiJob partsTypeIfiJob(){
        return new PartsTypeIfiJob();
    }

    /**
     * 物料类别
     * @return
     */
    @Bean
    public IfiMaterialTypeJob ifiMaterialTypeJob(){
        return new IfiMaterialTypeJob();
    }

    @Bean
    public IfiMeasureUnitJob ifiMeasureUnitJob(){
        return new IfiMeasureUnitJob();
    }

    @Bean(initMethod = "init")
    public JobScheduler colourIfiJobScheduler(final ColourIfiJob simpleJob, @Value("${colourIfiJob.cron}") final String cron,
                                              @Value("${colourIfiJob.shardingTotalCount}") final int shardingTotalCount,
                                              @Value("${colourIfiJob.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(simpleJob, regCenter, getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters));
    }

    @Bean(initMethod = "init")
    public JobScheduler styleTypeIfiJobScheduler(final StyleTypeIfiJob simpleJob, @Value("${styleTypeIfiJob.cron}") final String cron,
                                                 @Value("${styleTypeIfiJob.shardingTotalCount}") final int shardingTotalCount,
                                              @Value("${styleTypeIfiJob.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(simpleJob, regCenter, getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters));
    }

    @Bean(initMethod = "init")
    public JobScheduler componentTypeIfiJobScheduler(final ComponentTypeIfiJob simpleJob,
                                                     @Value("${componentTypeIfiJob.cron}") final String cron,
                                                     @Value("${componentTypeIfiJob.shardingTotalCount}") final int shardingTotalCount,
                                              @Value("${componentTypeIfiJob.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(simpleJob, regCenter, getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters));
    }

    @Bean(initMethod = "init")
    public JobScheduler partsTypeIfiJobScheduler(final PartsTypeIfiJob simpleJob,
                                                     @Value("${partsTypeIfiJob.cron}") final String cron,
                                                     @Value("${partsTypeIfiJob.shardingTotalCount}") final int shardingTotalCount,
                                                     @Value("${partsTypeIfiJob.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(simpleJob, regCenter, getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters));
    }

    @Bean(initMethod = "init")
    public JobScheduler ifiMaterialTypeJobScheduler(final IfiMaterialTypeJob simpleJob,
                                                 @Value("${ifiMaterialTypeJob.cron}") final String cron,
                                                 @Value("${ifiMaterialTypeJob.shardingTotalCount}") final int shardingTotalCount,
                                                 @Value("${ifiMaterialTypeJob.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(simpleJob, regCenter, getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters));
    }

    @Bean(initMethod = "init")
    public JobScheduler ifiMeasureUnitJobScheduler(final IfiMeasureUnitJob simpleJob,
                                                    @Value("${ifiMeasureUnitJob.cron}") final String cron,
                                                    @Value("${ifiMeasureUnitJob.shardingTotalCount}") final int shardingTotalCount,
                                                    @Value("${ifiMeasureUnitJob.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(simpleJob, regCenter, getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters));
    }

    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron, final int shardingTotalCount, final String shardingItemParameters) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(
                jobClass.getName(), cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).build(), jobClass.getCanonicalName())).overwrite(true).build();
    }
}
