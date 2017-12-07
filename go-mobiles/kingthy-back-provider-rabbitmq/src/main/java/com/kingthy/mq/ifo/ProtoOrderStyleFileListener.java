package com.kingthy.mq.ifo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingthy.dto.IfoOrderStyleFileDTO;
import com.kingthy.proto.ProtoOrderStyleFileInfo;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.IfoOrderInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 17:23 on 2017/10/20.
 * @Modified by:
 */
@Component
public class ProtoOrderStyleFileListener
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProtoOrderStyleFileListener.class);

    @Autowired
    private IfoOrderInfoService ifoOrderInfoService;

    public void receiveMessage(final Message message)
    {
        try {

            //转换数据
            ProtoOrderStyleFileInfo.OrderStyleFileInfo orderStyleFileInfo = ProtoOrderStyleFileInfo.OrderStyleFileInfo.parseFrom(message.getBody());

            //能过Gson 转换对象 ProtoOrderStyleFileInfo.OrderStyleFileInfo 转成 IfoOrderStyleFileDTO
            Gson gson = new Gson();

            //转换成JSON
            String json = gson.toJson(orderStyleFileInfo.getOrderStyleList());

            Type type = new TypeToken<ArrayList<IfoOrderStyleFileDTO.StyleInfo>>(){}.getType();

            //json转换对象
            List<IfoOrderStyleFileDTO.StyleInfo> styleFileInfos = gson.fromJson(json, type);

            IfoOrderStyleFileDTO ifoOrderStyleFileDTO = new IfoOrderStyleFileDTO();

            ifoOrderStyleFileDTO.setOrderItemSn(orderStyleFileInfo.getOrderItemSn());

            ifoOrderStyleFileDTO.setStyleInfoList(styleFileInfos);

            //调用接口创建CIPP订单信息
            WebApiResponse<?> response = ifoOrderInfoService.createOrderStyleFile(ifoOrderStyleFileDTO);

            if (response.getCode() != WebApiResponse.SUCCESS_CODE){

                LOGGER.error("同步CIPP订单款式文件业务失败 "+ response.getMessage());
                throw new AmqpRejectAndDontRequeueException("同步CIPP订单款式文件业务失败"+ response.getMessage());
            }


        }catch (Exception e){

            LOGGER.error("同步CIPP订单款式文件业务失败" + e);
            throw new AmqpRejectAndDontRequeueException("同步CIPP订单款式文件业务失败");
        }

    }
}
