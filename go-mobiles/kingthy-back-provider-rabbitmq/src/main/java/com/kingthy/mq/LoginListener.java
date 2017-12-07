package com.kingthy.mq;

import com.kingthy.constant.MemberMqNameConstans;
import com.kingthy.dto.MemberDTO;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户登录消息处理
 * @author Created by likejie on 2017/9/22.
 */
@Component
public class LoginListener {

    @Autowired
    private MemberService memberService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginListener.class);

    @RabbitListener(queues = MemberMqNameConstans.QUEUE_NAME)
    public void updateMember(final MemberDTO memberDto)
    {
        try
        {
            WebApiResponse<?> response = memberService.updateMember(memberDto);
            if (response.getCode() != WebApiResponse.SUCCESS_CODE){
                LOGGER.error("更新会员信息失败：" + response.getMessage());
                throw new AmqpRejectAndDontRequeueException("更新会员信息失败");
            }
        }catch (Exception e){
            LOGGER.error("更新会员信息失败" + e);
            throw new AmqpRejectAndDontRequeueException("更新会员信息失败");
        }
    }
}
