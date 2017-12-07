package com.kingthy.mq.ifo;

import com.google.gson.Gson;
import com.kingthy.constant.RabbitmqConstants;
import com.kingthy.dto.GeometryFileAddressDTO;
import com.kingthy.mq.ifo.NotifyCippOrderListener;
import com.kingthy.response.NcpltInfoResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 17:12 on 2017/10/17.
 * @Modified by:
 */
@Component
public class NcPltFileListener
{
    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyCippOrderListener.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queues = RabbitmqConstants.NCPLT_FILE_QUEUE)
    public void receiveMessage(final GeometryFileAddressDTO message)
    {
        try {

            //已经生成过了
            String redisKey = getNcPltRedisKey(message.getUuid());

            String json = stringRedisTemplate.opsForValue().get(redisKey);

            Gson gson = new Gson();

            NcpltInfoResp resp = gson.fromJson(json, NcpltInfoResp.class);

            //1:裁床 2:纸样
            if (GeometryFileAddressDTO.GeometryType.C.getValue() == message.getType()){
                resp.setPltPath(message.getFileAddress());
            }
            // 2:纸样
            else if (GeometryFileAddressDTO.GeometryType.P.getValue() == message.getType()){
                resp.setNcPath(message.getFileAddress());
            }
            stringRedisTemplate.opsForValue().set(redisKey, gson.toJson(resp).toString(),60 * 60 * 24, TimeUnit.SECONDS);

        }catch (Exception e){

            LOGGER.error("CIPP查询纸样裁床文件地址失败" + e);
        }

    }

    private String getNcPltRedisKey(String orderSn){
        StringBuffer key = new StringBuffer("NCPLT:");
        key.append(orderSn);
        return key.toString();
    }
}
