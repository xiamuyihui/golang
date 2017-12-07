package com.kingthy.service.impl;

import com.kingthy.constant.RabbitmqConstants;
import com.kingthy.dto.UploadModelImageDTO;
import com.kingthy.entity.ModelImage;
import com.kingthy.service.ModelService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * ModelServiceImpl(描述其作用)
 * <p>
 * @author 赵生辉 2017年06月13日 14:05
 *
 * @version 1.0.0
 */
@Service("modelService")
public class ModelServiceImpl implements ModelService
{
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public int sendImage(ModelImage modelImage)
    {
        //发送消息执行上传服务
        UploadModelImageDTO uploadModelImageDTO = new UploadModelImageDTO();
        uploadModelImageDTO.setFlankImage(modelImage.getFlankImage());
        uploadModelImageDTO.setFrontImage(modelImage.getFrontImage());
        uploadModelImageDTO.setModelUuid(modelImage.getModelUuid());
        uploadModelImageDTO.setToken(modelImage.getToken());
        uploadModelImageDTO.setCreateDate(new Date());
        rabbitTemplate.convertAndSend(RabbitmqConstants.MODEL_IMAGE_LISTENER_QUEUE, uploadModelImageDTO);
        return 1;
    }
}
