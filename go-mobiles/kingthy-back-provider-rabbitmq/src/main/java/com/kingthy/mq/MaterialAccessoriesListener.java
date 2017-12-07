package com.kingthy.mq;

import com.kingthy.constant.RabbitmqConstants;
import com.kingthy.dto.MaterialAccessoriesDTO;
import com.kingthy.service.MaterialAccessoriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xumin
 * @Description: 面辅料接口表同步
 * @DATE Created by 14:52 on 2017/9/15.
 * @Modified by:
 */
@Component
public class MaterialAccessoriesListener
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MaterialAccessoriesListener.class);

    @Autowired
    private MaterialAccessoriesService materialAccessoriesService;

    @RabbitListener(queues = RabbitmqConstants.MATERIAL_ACCESSORIES_LISTENER_QUEUE)
    public void receiveMessage(final MaterialAccessoriesDTO message)
    {
        try {

            int result = materialAccessoriesService.syncCIPPInfo(message);

            if (result <= 0 ){
                LOGGER.error("面辅料接口表同步 失败");
                throw new AmqpRejectAndDontRequeueException("面辅料接口表同步 失败");
            }

        }catch (Exception e){

            LOGGER.error("面辅料接口表同步,失败" + e);
            throw new AmqpRejectAndDontRequeueException("面辅料接口表同步");
        }

    }

}
