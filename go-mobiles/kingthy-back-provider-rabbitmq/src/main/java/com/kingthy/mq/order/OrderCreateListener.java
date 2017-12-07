package com.kingthy.mq.order;

import com.kingthy.constant.RabbitmqConstants;
import com.kingthy.dto.OrderCreateMqDTO;
import com.kingthy.service.OrderCreateService;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 保存订单信息
 * @author xumin
 * @Description:
 * @DATE Created by 15:35 on 2017/11/14.
 * @Modified by:
 */
@Component
public class OrderCreateListener
{
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCreateListener.class);

    @Autowired
    private OrderCreateService orderCreateService;

    @RabbitListener(queues = RabbitmqConstants.MALL_CREATE_LISTENER_QUEUE)
//    @RabbitListener(queues = "dlq.kingthy.mall.order.queue")
    public void receiveMessage(final OrderCreateMqDTO message){

        try {

            orderCreateService.create(message);

        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("保存订单信息 失败" + e);
            throw new AmqpRejectAndDontRequeueException("保存订单信息--业务层处理失败");
        }
    }
}
