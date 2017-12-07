package com.kingthy.conf;

import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;


/**
 * @author xumin
 * @Description:
 * @DATE Created by 10:15 on 2017/5/12.
 * @Modified by:
 */
@Configuration
public class RabbitmqConfig implements RabbitListenerConfigurer{

/*
    public static final String QUEUE_NAME = "file.buyers.show.queue";
    public static final String EXCHANGE_NAME = "topic.file.upload";
    public static final String ROUTING_KEY = "routing.file.buyers";
    */

/*
    @Bean
    public Queue appQueueUploadFile(){
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public TopicExchange appExchangeUploadFile(){
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding declareBinding(){
        return BindingBuilder.bind(appQueueUploadFile()).to(appExchangeUploadFile()).with(ROUTING_KEY);
    }
    */

    // You can comment all methods below and remove interface's implementation to use the default serialization / deserialization
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        rabbitTemplate.setChannelTransacted(true);
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }

    @Override
    public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }
}
