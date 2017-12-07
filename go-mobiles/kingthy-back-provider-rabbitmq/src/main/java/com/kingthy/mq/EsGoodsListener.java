package com.kingthy.mq;

import com.kingthy.constant.RabbitmqConstants;
import com.kingthy.dto.EsGoodsRabbitDTO;
import com.kingthy.service.EsGoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xumin
 * @Description: 商品信息同步到elasticsearch
 * @DATE Created by 15:54 on 2017/5/26.
 * @Modified by:
 */
@Component
public class EsGoodsListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(EsGoodsListener.class);

    @Autowired
    private EsGoodsService esGoodsService;

    @RabbitListener(queues = RabbitmqConstants.ES_GOODS_LISTENER_QUEUE)
    public void receiveMessage(final EsGoodsRabbitDTO message){

        try{

            esGoodsService.syncEsGoods(message.getGoodsUuid());

        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("商品信息同步到elasticsearch失败" + e);
            throw new AmqpRejectAndDontRequeueException("业务层处理失败");
        }
    }

}
