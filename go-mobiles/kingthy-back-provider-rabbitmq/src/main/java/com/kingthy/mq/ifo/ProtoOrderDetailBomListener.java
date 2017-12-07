package com.kingthy.mq.ifo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingthy.dto.IfoOrderDetailBomDTO;
import com.kingthy.proto.ProtoOrderDetailBom;
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
 * @Description: 同步BOM接口表
 * @DATE Created by 15:22 on 2017/10/18.
 * @Modified by:
 */
@Component
public class ProtoOrderDetailBomListener
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProtoOrderDetailBomListener.class);

    @Autowired
    private IfoOrderInfoService ifoOrderInfoService;

    public void receiveMessage(final Message message)
    {
        try {

            //转换数据
            ProtoOrderDetailBom.OrderDetailBom orderDetailBom = ProtoOrderDetailBom.OrderDetailBom.parseFrom(message.getBody());

            //通过 Gson 转换 对象
            Gson gson = new Gson();

            //转换成json
            String json = gson.toJson(orderDetailBom.getOrderBomList());

            Type type = new TypeToken<ArrayList<IfoOrderDetailBomDTO.BomInfo>>(){}.getType();

            List<IfoOrderDetailBomDTO.BomInfo> boomInfoList = gson.fromJson(json, type);

            //封装对象
            IfoOrderDetailBomDTO ifoOrderDetailBomDTO = new IfoOrderDetailBomDTO();

            ifoOrderDetailBomDTO.setOrderItemSn(orderDetailBom.getOrderItemSn());

            ifoOrderDetailBomDTO.setBomInfoList(boomInfoList);

            //调用接口创建CIPP订单信息
            WebApiResponse<?> response = ifoOrderInfoService.createOrderItemBom(ifoOrderDetailBomDTO);

            if (response.getCode() != WebApiResponse.SUCCESS_CODE){

                LOGGER.error("同步CIPP订单BOM信息业务失败 "+ response.getMessage());
                throw new AmqpRejectAndDontRequeueException("同步CIPP订单BOM信息业务失败"+ response.getMessage());
            }


        }catch (Exception e){

            LOGGER.error("同步CIPP订单BOM信息业务失败" + e);
            throw new AmqpRejectAndDontRequeueException("同步CIPP订单BOM信息业务失败");
        }

    }

}
