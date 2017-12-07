package com.kingthy.mq;


import com.kingthy.constant.RabbitmqConstants;
import com.kingthy.dto.OrderCommentImgDTO;
import com.kingthy.service.BuyersShowImgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author xumin
 * @Description:
 * @DATE Created by 11:43 on 2017/5/12.
 * @Modified by:
 */
@Component
public class BuyersShowListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuyersShowListener.class);

    @Autowired
    private BuyersShowImgService buyersShowImgService;

    @RabbitListener(queues = RabbitmqConstants.BUYERS_SHOW_LISTENER_QUEUE)
    public void receiveMessage(final OrderCommentImgDTO message){

        try {
            buyersShowImgService.sensitiveDataFilter(message);

        }catch (Exception e){
            LOGGER.error("买家秀保存图片失败" + e);
            throw new AmqpRejectAndDontRequeueException("业务层处理失败");
        }
    }
}
