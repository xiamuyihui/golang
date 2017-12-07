package com.kingthy.mq.ifo;

import com.kingthy.constant.RabbitmqConstants;
import com.kingthy.dto.IfoOrderDTO;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.IfoOrderInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xumin
 * @Description: 同步CIPP订单信息
 * @DATE Created by 14:58 on 2017/10/12.
 * @Modified by:
 */
@Component
public class NotifyCippOrderListener
{
    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyCippOrderListener.class);

    @Autowired
    private IfoOrderInfoService ifoOrderInfoService;

    public void receiveMessage(final IfoOrderDTO message)
    {
        try {

            WebApiResponse<?> response = ifoOrderInfoService.createIfoOrder(message);

            if (response.getCode() != WebApiResponse.SUCCESS_CODE){

                LOGGER.error("同步CIPP订单信息业务失败 "+ response.getMessage());
                throw new AmqpRejectAndDontRequeueException("同步CIPP订单信息业务失败"+ response.getMessage());
            }

        }catch (Exception e){

            LOGGER.error("同步CIPP订单信息,失败" + e);
            throw new AmqpRejectAndDontRequeueException("同步CIPP订单信息 失败");
        }

    }
}
