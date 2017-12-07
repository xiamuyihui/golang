package com.kingthy.mq;

import com.kingthy.constant.RabbitmqConstants;
import com.kingthy.dto.UploadModelImageDTO;
import com.kingthy.service.ModelImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UploadImageListener(描述其作用)
 * <p>
 * @author 赵生辉 2017年06月13日 15:28
 *
 * @version 1.0.0
 */
@Component
public class UploadImageListener
{
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadImageListener.class);

    @Autowired
    private ModelImageService modelImageService;

    @RabbitListener(queues = RabbitmqConstants.MODEL_IMAGE_LISTENER_QUEUE)
    public void receiveMessage(final UploadModelImageDTO massage){

        try{

            modelImageService.uploadModelImage(massage);

        }catch (Exception e){

            LOGGER.error("上传模型图片失败" + e);
            throw new AmqpRejectAndDontRequeueException("业务层处理失败");
        }
    }
}
