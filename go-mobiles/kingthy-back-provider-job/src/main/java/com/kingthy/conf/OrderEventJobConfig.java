package com.kingthy.conf;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.kingthy.job.CartEventJob;
import com.kingthy.job.CheckSnNumberJob;
import com.kingthy.job.CreateOrderSnJob;
import com.kingthy.job.OrderEventJob;
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
public class OrderEventJobConfig {

    @Resource
    private ZookeeperRegistryCenter regCenter;

    @Bean
    public OrderEventJob orderEventJob() {
        return new OrderEventJob();
    }

    @Bean
    public CartEventJob cartEventJob(){
        return new CartEventJob();
    }

    @Bean
    public CreateOrderSnJob createOrderSnJob(){
        return new CreateOrderSnJob();
    }

    @Bean
    public CheckSnNumberJob checkSnNumberJob(){
        return new CheckSnNumberJob();
    }

    @Bean(initMethod = "init")
    public JobScheduler simpleJobScheduler(final OrderEventJob simpleJob, @Value("${orderEventJob.cron}") final String cron, @Value("${orderEventJob.shardingTotalCount}") final int shardingTotalCount,
                                           @Value("${orderEventJob.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(simpleJob, regCenter, getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters));
    }


    @Bean(initMethod = "init")
    public JobScheduler cartEventJobScheduler(final CartEventJob simpleJob, @Value("${cartEventJob.cron}") final String cron, @Value("${cartEventJob.shardingTotalCount}") final int shardingTotalCount,
                                           @Value("${cartEventJob.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(simpleJob, regCenter, getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters));
    }

    @Bean(initMethod = "init")
    public JobScheduler createOrderSnJobScheduler(final CreateOrderSnJob simpleJob, @Value("${createOrderSnJob.cron}") final String cron, @Value("${createOrderSnJob.shardingTotalCount}") final int shardingTotalCount,
                                              @Value("${createOrderSnJob.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(simpleJob, regCenter, getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters));
    }

    @Bean(initMethod = "init")
    public JobScheduler checkSnNumberJobScheduler(final CheckSnNumberJob simpleJob, @Value("${checkSnNumberJob.cron}") final String cron, @Value("${checkSnNumberJob.shardingTotalCount}") final int shardingTotalCount,
                                                  @Value("${checkSnNumberJob.shardingItemParameters}") final String shardingItemParameters) {
        return new SpringJobScheduler(simpleJob, regCenter, getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters));
    }


    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron, final int shardingTotalCount, final String shardingItemParameters) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(
                jobClass.getName(), cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).build(), jobClass.getCanonicalName())).overwrite(true).build();
    }
}
