package com.kingthy.mq;

import com.kingthy.constant.RabbitmqConstants;
import com.kingthy.dto.TransferAnswerDTO;
import com.kingthy.dto.TransferDTO;
import com.kingthy.dto.WithdrawsCashMqDTO;
import com.kingthy.entity.MemberWithdrawsCash;
import com.kingthy.request.UpdateWithDrawStatusReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.IncomeService;
import com.kingthy.service.UnionPayementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 提现到银行卡
 * @author xumin
 * @Description:
 * @DATE Created by 11:37 on 2017/6/20.
 * @Modified by:
 */
@Component
public class WithdrawCashListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(WithdrawCashListener.class);

    @Autowired
    private UnionPayementService unionPayementService;

    @Autowired
    IncomeService incomeService;

    @RabbitListener(queues = RabbitmqConstants.WITHDRAWS_CASH_LISTENER_QUEUE)
    public void receiveMessage(final WithdrawsCashMqDTO message){

        try{
            TransferDTO dto = new TransferDTO();
            dto.setAccNo(message.getAccNo());
            dto.setAmount(message.getAmount());
            dto.setCertifId(message.getCertifId());
            dto.setOrderId(message.getOrderId());
            //调用接口开始处理提现
            WebApiResponse<?> response = unionPayementService.sendTransferRequest(dto);

            //提现成功,保存到数据库
            if (WebApiResponse.SUCCESS_CODE == response.getCode()){
                TransferAnswerDTO daifuAnswerDTO = (TransferAnswerDTO) response.getData();

                UpdateWithDrawStatusReq req = new UpdateWithDrawStatusReq();
                req.setMemberUuid(message.getMemberUuid());
                req.setWithdrawsUuid(message.getWithdrawsUuid());
                req.setOrderId(daifuAnswerDTO.getOrderId());
                req.setStatus(MemberWithdrawsCash.StatusType.C_1.getValue());
                LOGGER.error("------------req------------ " + req.toString());
                incomeService.updateStatusAndOrderId(req);

            }else{
                LOGGER.error("调用提现接口失败: 错误代码："+response.getCode() +" 错误信息："+ response.getMessage());
            }

        }catch (Exception e){
            LOGGER.error("提现到银行卡失败" + e);
            throw new AmqpRejectAndDontRequeueException("提现到银行卡业务层处理失败");
        }
    }
}
