package com.kingthy.conf;

import com.alibaba.dubbo.config.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author :pany
 * @date:2016-11-3
 *
 */
@Component
public class DubboServiceConf
{
    @Bean
    public ConsumerConfig configDubboThread()
    {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setCheck(false);
        return consumerConfig;
    }
}
