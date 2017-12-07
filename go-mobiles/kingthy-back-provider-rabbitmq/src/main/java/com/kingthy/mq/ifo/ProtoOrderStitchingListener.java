package com.kingthy.mq.ifo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingthy.dto.IfoOrderStyleFileDTO;
import com.kingthy.dto.IfoStitchingStyleDTO;
import com.kingthy.proto.ProtoOrderStitchingInfo;
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
 * @DATE Created by 18:21 on 2017/10/20.
 * @Modified by:
 */
@Component
public class ProtoOrderStitchingListener
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProtoOrderStyleFileListener.class);

    @Autowired
    private IfoOrderInfoService ifoOrderInfoService;

    public void receiveMessage(final Message message)
    {
        try {

            //转换数据
            ProtoOrderStitchingInfo.OrderStitchingInfo orderStyleFileInfo = ProtoOrderStitchingInfo.OrderStitchingInfo.parseFrom(message.getBody());


            //通过Gson 转换对象 ProtoOrderStitchingInfo.OrderStitchingInfo 转成 IfoStitchingStyleDTO
            Gson gson = new Gson();

            //缝合关系数据 转成json
            String json = gson.toJson(orderStyleFileInfo.getStitchingList());

            Type type = new TypeToken<ArrayList<IfoStitchingStyleDTO.StitchingInfo>>(){}.getType();

            //json 转成 StitchingInfo 对象
            List<IfoStitchingStyleDTO.StitchingInfo> styleFileInfos = gson.fromJson(json, type);

            //封装数据
            IfoStitchingStyleDTO ifoStitchingStyleDTO = new IfoStitchingStyleDTO();

            ifoStitchingStyleDTO.setOrderItemSn(orderStyleFileInfo.getOrderItemSn());

            ifoStitchingStyleDTO.setStitchingInfoList(styleFileInfos);

            //调用接口创建CIPP订单缝合关系信息
            WebApiResponse<?> response = ifoOrderInfoService.createStitchingStyle(ifoStitchingStyleDTO);

            if (response.getCode() != WebApiResponse.SUCCESS_CODE){

                LOGGER.error("同步CIPP订单缝合关系业务失败 "+ response.getMessage());
                throw new AmqpRejectAndDontRequeueException("同步CIPP订单缝合关系业务失败"+ response.getMessage());
            }


        }catch (Exception e){

            LOGGER.error("同步CIPP订单缝合关系业务失败" + e);
            throw new AmqpRejectAndDontRequeueException("同步CIPP订单缝合关系业务失败");
        }

    }
}
