package com.kingthy.conf;

import com.kingthy.constant.RabbitmqConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * @AUTHORS xumin
 * @Description:
 * @DATE Created by 10:15 on 2017/5/12.
 * @Modified by:
 */
@Configuration
public class RabbitmqConfig implements RabbitListenerConfigurer{

    /**
     * 初始化 queue exchange
     *  BuyersShow 买家秀
     * begin
     */

    @Bean
    public Queue dlqBuyersShowQueue(){
        return new Queue(RabbitmqConstants.BuyersShowEnum.DLQ_QUEUE.getValue());
    }

    @Bean
    public TopicExchange dlxBuyersShowExchange(){
       return new TopicExchange(RabbitmqConstants.BuyersShowEnum.DLQ_EXCHANGE.getValue());
    }


    @Bean
    public Binding dlxBindingBuyersShow(){
        return BindingBuilder.bind(dlqBuyersShowQueue()).to(dlxBuyersShowExchange()).with(RabbitmqConstants.BuyersShowEnum.DLQ_ROUTING.getValue());
    }

    @Bean
    public Queue buyersShowQueue(){

        //rabbitmq dlq
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", RabbitmqConstants.BuyersShowEnum.DLQ_EXCHANGE.getValue());
        args.put("x-dead-letter-routing-key", RabbitmqConstants.BuyersShowEnum.DLQ_ROUTING.getValue());

        return new Queue(RabbitmqConstants.BuyersShowEnum.QUEUE.getValue(), true, false, false, args);
    }

    @Bean
    public TopicExchange buyersShowExchange(){
        return new TopicExchange(RabbitmqConstants.BuyersShowEnum.EXCHANGE.getValue());
    }

    @Bean
    public Binding declareBindingBuyersShow(){
        return BindingBuilder.bind(buyersShowQueue()).to(buyersShowExchange()).with(RabbitmqConstants.BuyersShowEnum.ROUTING.getValue());
    }

    /**
     * elasticsearch goods
     * @return
     */
    @Bean
    public Queue esGoodsQueue(){

        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", RabbitmqConstants.EsGoodsEnum.DLQ_EXCHANGE.getValue());
        args.put("x-dead-letter-routing-key", RabbitmqConstants.EsGoodsEnum.DLQ_ROUTING.getValue());

        return new Queue(RabbitmqConstants.EsGoodsEnum.QUEUE.getValue(), true, false, false, args);
    }

    @Bean
    public TopicExchange esGoodsExchange(){
        return new TopicExchange(RabbitmqConstants.EsGoodsEnum.EXCHANGE.getValue());
    }

    @Bean
    public Binding declareBindingEsGoods(){
        return BindingBuilder.bind(esGoodsQueue()).to(esGoodsExchange()).with(RabbitmqConstants.EsGoodsEnum.ROUTING.getValue());
    }


    /**
     * elasticsearch goods dlq
     * @return
     */
    @Bean
    public Queue dlqEsGoodsQueue(){
        return new Queue(RabbitmqConstants.EsGoodsEnum.DLQ_QUEUE.getValue());
    }

    @Bean
    public TopicExchange dlxEsGoodsExchange(){
        return new TopicExchange(RabbitmqConstants.EsGoodsEnum.DLQ_EXCHANGE.getValue());
    }

    @Bean
    public Binding dlxBindingEsGoods(){
        return BindingBuilder.bind(dlqEsGoodsQueue()).to(dlxEsGoodsExchange()).with(RabbitmqConstants.EsGoodsEnum.DLQ_ROUTING.getValue());
    }


    /**
     * 初始化 queue exchange
     *
     * end
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
