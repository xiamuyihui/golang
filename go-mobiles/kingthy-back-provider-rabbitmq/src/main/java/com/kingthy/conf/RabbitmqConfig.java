package com.kingthy.conf;

import com.kingthy.constant.CacheMqNameConstans;
import com.kingthy.constant.MemberMqNameConstans;
import com.kingthy.constant.RabbitmqConstants;
import com.kingthy.constant.UnionpayMqNameConstans;
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
 * @author xumin
 * @Description:
 * @DATE Created by 10:15 on 2017/5/12.
 * @Modified by:
 */
@Configuration
public class RabbitmqConfig implements RabbitListenerConfigurer{
    
    public final String EXCHANGE = "x-dead-letter-exchange";
    
    public final String ROUTING = "x-dead-letter-routing-key";

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
        Map<String, Object> args = new HashMap<>(2);
        args.put(EXCHANGE, RabbitmqConstants.BuyersShowEnum.DLQ_EXCHANGE.getValue());
        args.put(ROUTING, RabbitmqConstants.BuyersShowEnum.DLQ_ROUTING.getValue());

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

        Map<String, Object> args = new HashMap<>(2);
        args.put(EXCHANGE, RabbitmqConstants.EsGoodsEnum.DLQ_EXCHANGE.getValue());
        args.put(ROUTING, RabbitmqConstants.EsGoodsEnum.DLQ_ROUTING.getValue());

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
     * 初始化 增加收益 queue
     * begin
     * @return
     */
    @Bean
    public Queue orderIncomeQueue(){

        Map<String, Object> args = new HashMap<>(2);
        args.put(EXCHANGE, RabbitmqConstants.OrderIncomeEnum.DLQ_EXCHANGE.getValue());
        args.put(ROUTING, RabbitmqConstants.OrderIncomeEnum.DLQ_ROUTING.getValue());

        return new Queue(RabbitmqConstants.OrderIncomeEnum.QUEUE.getValue(), true, false, false, args);
    }

    @Bean
    public TopicExchange orderIncomeExchange(){
        return new TopicExchange(RabbitmqConstants.OrderIncomeEnum.EXCHANGE.getValue());
    }

    @Bean
    public Binding declareBindingOrderIncome(){
        return BindingBuilder.bind(orderIncomeQueue()).to(orderIncomeExchange()).with(RabbitmqConstants.OrderIncomeEnum.ROUTING.getValue());
    }

    /**
     * 增加收益 dlq queue
     * @return
     */
    @Bean
    public Queue dlqOrderIncomeQueue(){
        return new Queue(RabbitmqConstants.OrderIncomeEnum.DLQ_QUEUE.getValue());
    }

    @Bean
    public TopicExchange dlxOrderIncomeExchange(){
        return new TopicExchange(RabbitmqConstants.OrderIncomeEnum.DLQ_EXCHANGE.getValue());
    }

    @Bean
    public Binding dlxBindingOrderIncome(){
        return BindingBuilder.bind(dlqOrderIncomeQueue()).to(dlxOrderIncomeExchange()).with(RabbitmqConstants.OrderIncomeEnum.DLQ_ROUTING.getValue());
    }

    /**
     * 初始化 增加收益 queue
     * end
     * @return
     */

    @Bean
    public Queue modelImageQueue(){

        Map<String, Object> args = new HashMap<>(2);
        args.put(EXCHANGE, RabbitmqConstants.UploadModelImageEnum.DLQ_EXCHANGE.getValue());
        args.put(ROUTING, RabbitmqConstants.UploadModelImageEnum.DLQ_ROUTING.getValue());

        return new Queue(RabbitmqConstants.UploadModelImageEnum.QUEUE.getValue(), true, false, false, args);
    }

    @Bean
    public TopicExchange modelImageExchange(){
        return new TopicExchange(RabbitmqConstants.UploadModelImageEnum.EXCHANGE.getValue());
    }

    @Bean
    public Binding declareBindingModelImage(){
        return BindingBuilder.bind(modelImageQueue()).to(modelImageExchange()).with(RabbitmqConstants.UploadModelImageEnum.ROUTING.getValue());
    }


    /**
     * model image dlq
     * @return
     */
    @Bean
    public Queue dlqModelImageQueue(){
        return new Queue(RabbitmqConstants.UploadModelImageEnum.DLQ_QUEUE.getValue());
    }

    @Bean
    public TopicExchange dlxModelImageExchange(){
        return new TopicExchange(RabbitmqConstants.UploadModelImageEnum.DLQ_EXCHANGE.getValue());
    }

    @Bean
    public Binding dlxBindingModelImage(){
        return BindingBuilder.bind(dlqModelImageQueue()).to(dlxModelImageExchange()).with(RabbitmqConstants.UploadModelImageEnum.DLQ_ROUTING.getValue());
    }

    /**
     * 更新作品的状态
     * @return
     */
    @Bean
    public Queue dlqUpdateOpusQueue(){
        return new Queue(RabbitmqConstants.UpdateOpusEnum.DLQ_QUEUE.getValue());
    }

    @Bean
    public TopicExchange dlxUpdateOpusExchange(){
        return new TopicExchange(RabbitmqConstants.UpdateOpusEnum.DLQ_EXCHANGE.getValue());
    }

    @Bean
    public Binding dlxBindingUpdateOpus(){
        return BindingBuilder.bind(dlqUpdateOpusQueue()).to(dlxUpdateOpusExchange()).with(RabbitmqConstants.UpdateOpusEnum.DLQ_ROUTING.getValue());
    }


    @Bean
    public Queue updateOpusQueue(){

        Map<String, Object> args = new HashMap<>(2);
        args.put(EXCHANGE, RabbitmqConstants.UpdateOpusEnum.DLQ_EXCHANGE.getValue());
        args.put(ROUTING, RabbitmqConstants.UpdateOpusEnum.DLQ_ROUTING.getValue());

        return new Queue(RabbitmqConstants.UpdateOpusEnum.QUEUE.getValue(), true, false, false, args);
    }

    @Bean
    public TopicExchange updateOpusExchange(){
        return new TopicExchange(RabbitmqConstants.UpdateOpusEnum.EXCHANGE.getValue());
    }

    @Bean
    public Binding declareBindingupdateOpus(){
        return BindingBuilder.bind(updateOpusQueue()).to(updateOpusExchange()).with(RabbitmqConstants.UpdateOpusEnum.ROUTING.getValue());
    }

    /**
     * 提现
     * @return
     */
    @Bean
    public Queue withdrawsCashQueue(){

        Map<String, Object> args = new HashMap<>(2);
        args.put(EXCHANGE, RabbitmqConstants.WithdrawsCashEnum.DLQ_EXCHANGE.getValue());
        args.put(ROUTING, RabbitmqConstants.WithdrawsCashEnum.DLQ_ROUTING.getValue());

        return new Queue(RabbitmqConstants.WithdrawsCashEnum.QUEUE.getValue(), true, false, false, args);
    }

    @Bean
    public TopicExchange withdrawsCashExchange(){
        return new TopicExchange(RabbitmqConstants.WithdrawsCashEnum.EXCHANGE.getValue());
    }

    @Bean
    public Binding declareBindingWithdrawsCash(){
        return BindingBuilder.bind(withdrawsCashQueue()).to(withdrawsCashExchange()).with(RabbitmqConstants.WithdrawsCashEnum.ROUTING.getValue());
    }

    /**
     * 增加收益 dlq queue
     * @return
     */
    @Bean
    public Queue dlqWithdrawsCashQueue(){
        return new Queue(RabbitmqConstants.WithdrawsCashEnum.DLQ_QUEUE.getValue());
    }

    @Bean
    public TopicExchange dlxWithdrawsCashExchange(){
        return new TopicExchange(RabbitmqConstants.WithdrawsCashEnum.DLQ_EXCHANGE.getValue());
    }

    @Bean
    public Binding dlxBindingWithdrawsCash(){
        return BindingBuilder.bind(dlqWithdrawsCashQueue()).to(dlxWithdrawsCashExchange()).with(RabbitmqConstants.WithdrawsCashEnum.DLQ_ROUTING.getValue());
    }

    /**
     * 初始化 queue exchange
     *
     * end
     */


    /**
     *
     * 创建交换器
     * @return
     */
    @Bean
    public TopicExchange defaultExchangeCache(){

        return new TopicExchange(CacheMqNameConstans.EXCHANGE_NAME);
    }
    /**
     *
     * 创建队列
     * @return
     */
    @Bean
    public Queue queueCache(){

        return new Queue(CacheMqNameConstans.QUEUE_NAME);
    }
    /**
     *
     * 绑定队列到交换器
     * @return
     */
    @Bean
    public Binding bindingQueueToExchangeCache() {

        return BindingBuilder.bind(queueCache()).to(defaultExchangeCache()).with(CacheMqNameConstans.ROUTING_KEY_NAME);
    }

    /**
     *
     * 创建交换器
     * @return
     */
    @Bean
    public TopicExchange defaultExchangeUnionpay(){

        return new TopicExchange(UnionpayMqNameConstans.EXCHANGE_NAME);
    }
    /**
     *
     * 创建队列
     * @return
     */
    @Bean
    public Queue queueUnionpay(){

        return new Queue(UnionpayMqNameConstans.QUEUE_NAME);
    }
    /**
     *
     * 绑定队列到交换器
     * @return
     */
    @Bean
    public Binding bindingQueueToExchangeUnionpay() {

        return BindingBuilder.bind(queueUnionpay()).to(defaultExchangeUnionpay()).with(UnionpayMqNameConstans.ROUTING_KEY_NAME);
    }



    /**********渲染队列 start****************/

    @Bean
    public Queue renderVideoQueue(){

        return new Queue(RabbitmqConstants.RENDER_VIDEO_QUEUE);
    }
    @Bean
    public TopicExchange renderVideoExchange(){

        return new TopicExchange(RabbitmqConstants.RENDER_VIDEO_EXCHANGE);
    }
    @Bean
    public Binding renderVideoBindingQueueToExchange() {

        return BindingBuilder.bind(renderVideoQueue()).to(renderVideoExchange()).with(RabbitmqConstants.RENDER_VIDEO_ROUTING);
    }
    /**********渲染队列 end******************/



    /**
     * 短信队列
     * @return
     */
    @Bean
    public Queue queueSmsMessage(){

        //处理消息失败 发送到此队列
        Map<String, Object> args = new HashMap<>(2);
        args.put(EXCHANGE, RabbitmqConstants.SmsMessageEnum.DLQ_EXCHANGE.getValue());
        args.put(ROUTING, RabbitmqConstants.SmsMessageEnum.DLQ_ROUTING.getValue());

        return new Queue(RabbitmqConstants.SmsMessageEnum.QUEUE.getValue(), true, false, false, args);
    }

    /**
     * 备用队列
     * @return
     */
    @Bean
    public Queue queueSmsMessageOther(){
        return new Queue(RabbitmqConstants.SmsMessageEnum.QUEUE_A.getValue());
    }

    @Bean
    public TopicExchange defaultExchangeSmsMessage() {
        return new TopicExchange(RabbitmqConstants.SmsMessageEnum.EXCHANGE.getValue());
    }

    @Bean
    public Binding bindingQueueToExchangeSmsMessage() {

        return BindingBuilder.bind(queueSmsMessage()).to(defaultExchangeSmsMessage()).with(RabbitmqConstants.SmsMessageEnum.ROUTING.getValue());
    }

    /**
     * 消息处理失败消息转发到此队列
     * @return
     */
    @Bean
    public Queue dlqQueueSmsMessage(){
        return new Queue(RabbitmqConstants.SmsMessageEnum.DLQ_QUEUE.getValue());
    }

    @Bean
    public TopicExchange dlxExchangeSmsMessage() {
        return new TopicExchange(RabbitmqConstants.SmsMessageEnum.DLQ_EXCHANGE.getValue());
    }

    @Bean
    public Binding dlqBindingQueueToExchangeSmsMessage() {

        return BindingBuilder.bind(dlqQueueSmsMessage()).to(dlxExchangeSmsMessage()).with(RabbitmqConstants.SmsMessageEnum.DLQ_ROUTING.getValue());
    }

    /*********更新会员信息队列statr***********/
    @Bean
    public TopicExchange defaultExchangeMember(){

        return new TopicExchange(MemberMqNameConstans.EXCHANGE_NAME);
    }
    @Bean
    public Queue queueMember(){

        return new Queue(MemberMqNameConstans.QUEUE_NAME);
    }
    @Bean
    public Binding bindingQueueToExchangeCMember() {

        return BindingBuilder.bind(queueMember()).to(defaultExchangeMember()).with(MemberMqNameConstans.ROUTING_KEY_NAME);
    }
    /*********更新会员信息队列end***********/


    /**
     * 订单事件队列 begin
     * @return
     */
    @Bean
    public Queue dlqOrderEventQueue(){
        return new Queue(RabbitmqConstants.OrderEventEnum.DLQ_QUEUE.getValue());
    }

    @Bean
    public TopicExchange dlxOrderEventExchange(){
        return new TopicExchange(RabbitmqConstants.OrderEventEnum.DLQ_EXCHANGE.getValue());
    }


    @Bean
    public Binding dlxBindingOrderEvent(){
        return BindingBuilder.bind(dlqOrderEventQueue()).to(dlxOrderEventExchange()).with(RabbitmqConstants.OrderEventEnum.DLQ_ROUTING.getValue());
    }

    @Bean
    public Queue orderEventQueue(){

        //rabbitmq dlq
        Map<String, Object> args = new HashMap<>(2);
        args.put(EXCHANGE, RabbitmqConstants.OrderEventEnum.DLQ_EXCHANGE.getValue());
        args.put(ROUTING, RabbitmqConstants.OrderEventEnum.DLQ_ROUTING.getValue());

        return new Queue(RabbitmqConstants.OrderEventEnum.QUEUE.getValue(), true, false, false, args);
    }

    @Bean
    public TopicExchange orderEventExchange(){
        return new TopicExchange(RabbitmqConstants.OrderEventEnum.EXCHANGE.getValue());
    }

    @Bean
    public Binding declareBindingOrderEvent(){
        return BindingBuilder.bind(orderEventQueue()).to(orderEventExchange()).with(RabbitmqConstants.OrderEventEnum.ROUTING.getValue());
    }

    /**
     * 订单事件队列 end
     */

    /**
     * 同步面辅料到数据中心 begin
     * @return
     */

    @Bean
    public Queue dlqMaterialQueue(){
        return new Queue(RabbitmqConstants.MaterialAccessories.DLQ_QUEUE.getValue());
    }

    @Bean
    public TopicExchange dlxMaterialExchange(){
        return new TopicExchange(RabbitmqConstants.MaterialAccessories.DLQ_EXCHANGE.getValue());
    }


    @Bean
    public Binding dlxBindingMaterial(){
        return BindingBuilder.bind(dlqMaterialQueue()).to(dlxMaterialExchange()).with(RabbitmqConstants.MaterialAccessories.DLQ_ROUTING.getValue());
    }

    @Bean
    public Queue materialQueue(){

        //rabbitmq dlq
        Map<String, Object> args = new HashMap<>(2);
        args.put(EXCHANGE, RabbitmqConstants.MaterialAccessories.DLQ_EXCHANGE.getValue());
        args.put(ROUTING, RabbitmqConstants.MaterialAccessories.DLQ_ROUTING.getValue());

        return new Queue(RabbitmqConstants.MaterialAccessories.QUEUE.getValue(), true, false, false, args);
    }

    @Bean
    public TopicExchange materialExchange(){
        return new TopicExchange(RabbitmqConstants.MaterialAccessories.EXCHANGE.getValue());
    }

    @Bean
    public Binding declareBindingMaterial(){
        return BindingBuilder.bind(materialQueue()).to(materialExchange()).with(RabbitmqConstants.MaterialAccessories.ROUTING.getValue());
    }

    /**
     * 同步面辅料到数据中心 end
     * @return
     */


    /**
     * 同步订单信息到数据中心 begin
     * @return
     */
    @Bean
    public Queue dlqNotifyCippQueue(){
        return new Queue(RabbitmqConstants.NotifyCipp.DLQ_QUEUE.getValue());
    }

    @Bean
    public TopicExchange dlxNotifyCippExchange(){
        return new TopicExchange(RabbitmqConstants.NotifyCipp.DLQ_EXCHANGE.getValue());
    }


    @Bean
    public Binding dlxBindingNotifyCipp(){
        return BindingBuilder.bind(dlqNotifyCippQueue()).to(dlxNotifyCippExchange()).with(RabbitmqConstants.NotifyCipp.DLQ_ROUTING.getValue());
    }

    @Bean
    public Queue notifyCippQueue(){

        //rabbitmq dlq
        Map<String, Object> args = new HashMap<>(2);
        args.put(EXCHANGE, RabbitmqConstants.NotifyCipp.DLQ_EXCHANGE.getValue());
        args.put(ROUTING, RabbitmqConstants.NotifyCipp.DLQ_ROUTING.getValue());

        return new Queue(RabbitmqConstants.NotifyCipp.QUEUE.getValue(), true, false, false, args);
    }

    @Bean
    public TopicExchange notifyCippExchange(){
        return new TopicExchange(RabbitmqConstants.NotifyCipp.EXCHANGE.getValue());
    }

    @Bean
    public Binding declareBindingNotifyCipp(){
        return BindingBuilder.bind(notifyCippQueue()).to(notifyCippExchange()).with(RabbitmqConstants.NotifyCipp.ROUTING.getValue());
    }

    /**
     * 同步订单信息到数据中心 end
     * @return
     */

    /*********CIPP查询纸样裁床文件队列statr***********/
    @Bean
    public TopicExchange ncPltCippExchange(){

        return new TopicExchange(RabbitmqConstants.NCPLT_FILE_EXCHANGE);
    }
    @Bean
    public Queue ncPltCippQueue(){

        return new Queue(RabbitmqConstants.NCPLT_FILE_QUEUE);
    }
    @Bean
    public Binding declareBindingNcPlt() {

        return BindingBuilder.bind(ncPltCippQueue()).to(ncPltCippExchange()).with(RabbitmqConstants.NCPLT_FILE_ROUTING);
    }
    /*********CIPP查询纸样裁床文件队列end***********/


    /**创建订单队列 begin */
    @Bean
    public Queue dlqMallCreateOrderQueue(){
        return new Queue(RabbitmqConstants.MallCreateOrder.DLQ_QUEUE.getValue());
    }

    @Bean
    public TopicExchange dlxMallCreateOrderExchange(){
        return new TopicExchange(RabbitmqConstants.MallCreateOrder.DLQ_EXCHANGE.getValue());
    }


    @Bean
    public Binding dlxBindingMallCreateOrder(){
        return BindingBuilder.bind(dlqMallCreateOrderQueue()).to(dlxMallCreateOrderExchange())
                .with(RabbitmqConstants.MallCreateOrder.DLQ_ROUTING.getValue());
    }

    @Bean
    public Queue mallCreateOrderQueue(){

        //rabbitmq dlq
        Map<String, Object> args = new HashMap<>(2);
        args.put(EXCHANGE, RabbitmqConstants.MallCreateOrder.DLQ_EXCHANGE.getValue());
        args.put(ROUTING, RabbitmqConstants.MallCreateOrder.DLQ_ROUTING.getValue());

        return new Queue(RabbitmqConstants.MallCreateOrder.QUEUE.getValue(), true, false, false, args);
    }

    @Bean
    public TopicExchange mallCreateOrderExchange(){
        return new TopicExchange(RabbitmqConstants.MallCreateOrder.EXCHANGE.getValue());
    }

    @Bean
    public Binding declareBindingMallCreateOrder(){
        return BindingBuilder.bind(mallCreateOrderQueue()).to(mallCreateOrderExchange()).with(RabbitmqConstants.MallCreateOrder.ROUTING.getValue());
    }

    /**创建订单队列 end */


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
