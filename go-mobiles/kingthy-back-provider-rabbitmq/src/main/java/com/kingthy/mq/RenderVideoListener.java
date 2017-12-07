package com.kingthy.mq;

import com.kingthy.constant.RabbitmqConstants;
import com.kingthy.dto.RenderVideoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 监听渲染器，走秀视频渲染
 * @author Created by likejie on 2017/10/11.
 */
@Component
public class RenderVideoListener {

    private static final Logger LOG = LoggerFactory.getLogger(RenderVideoListener.class);

    @RabbitListener(queues = RabbitmqConstants.RENDER_VIDEO_QUEUE)
    public void renderVideo(RenderVideoDTO dto)
    {
        try
        {

        }catch (Exception e){
            LOG.error(e.toString());
            throw new AmqpRejectAndDontRequeueException("异常");
        }
    }
}
