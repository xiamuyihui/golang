package com.kingthy.conf;


import com.kingthy.constant.CacheMqNameConstans;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * 创建队列
 * Created by likejie on 2017/5/26.
 */
@Configuration
public class CacheUpdateQueueConfig {


    /**
     *
     * 创建交换器
     * @return
     */
    @Bean
    public TopicExchange  defaultExchange(){

        return new TopicExchange(CacheMqNameConstans.EXCHANGE_NAME);
    }
    /**
     *
     * 创建队列
     * @return
     */
    @Bean
    public Queue queue(){

        return new Queue(CacheMqNameConstans.QUUE_NAME);
    }
    /**
     *
     * 绑定队列到交换器
     * @return
     */
    @Bean
    public Binding bindingQueueToExchange() {

        return BindingBuilder.bind(queue()).to(defaultExchange()).with(CacheMqNameConstans.ROUTING_KEY_NAME);
    }
}
