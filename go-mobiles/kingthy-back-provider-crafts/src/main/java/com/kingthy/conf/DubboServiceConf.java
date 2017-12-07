package com.kingthy.conf;

import com.alibaba.dubbo.config.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author 潘勇
 * @Data 2017/9/29 18:22.
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
