package com.kingthy.mq;

import com.kingthy.constant.RabbitmqConstants;
import com.kingthy.request.EventPublishReq;
import com.kingthy.service.EventProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 14:59 on 2017/8/23.
 * @Modified by:
 */
@Component
public class OrderEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEventListener.class);

    @Autowired
    private EventProcessService eventProcessService;

    @RabbitListener(queues = RabbitmqConstants.ORDER_EVENT_LISTENER_QUEUE)
    public void receiveMessage(final EventPublishReq message)
    {

        try {
            int result = eventProcessService.insert(message);

            if (result <= 0 ){
                LOGGER.error("[购物车]-新增待处理事件失败");
                throw new AmqpRejectAndDontRequeueException("[购物车]-新增待处理事件失败");
            }

        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("购物车接收消息, 新增待处理事件失败" + e);
            throw new AmqpRejectAndDontRequeueException("购物车接收消息, 新增待处理事件失败");
        }
    }
}
