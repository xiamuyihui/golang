package com.kingthy.mq;

import com.kingthy.constant.RabbitmqConstants;
import com.kingthy.dto.OrderIncomeMqDTO;
import com.kingthy.request.IncomeAddReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.IncomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xumin
 * @Description:  增加收益
 * @DATE Created by 14:36 on 2017/6/14.
 * @Modified by:
 */
@Component
public class OrderIncomeListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderIncomeListener.class);

    @Autowired
    private IncomeService incomeService;

    @RabbitListener(queues = RabbitmqConstants.ORDER_INCOME_LISTENER_QUEUE)
    public void receiveMessage(final OrderIncomeMqDTO message){

        try{

            IncomeAddReq req = new IncomeAddReq();
            req.setMemberUuid(message.getMemberUuid());
            req.setOrderSn(message.getOrderSn());

            WebApiResponse<?> response = incomeService.addIncome(req);

            if (response.getCode() != WebApiResponse.SUCCESS_CODE){
                LOGGER.error("增加收益请求接口失败" + response.getMessage());
                throw new AmqpRejectAndDontRequeueException("业务层处理失败");
            }
        }catch (Exception e){
            LOGGER.error("增加收益失败" + e);
            throw new AmqpRejectAndDontRequeueException("增加收益业务层处理失败");
        }
    }
}
