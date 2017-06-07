package com.kingthy.mq;


import com.kingthy.constant.CacheMqNameConstans;
import com.kingthy.dto.UpdateCacheQueueDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;




/**
 *
 * 队列（缓存更新）消息消费
 * Created by likejie on 2017/5/27.
 */

@Component
public class CacheUpdateListener {

    private static final Logger logger = LoggerFactory.getLogger(CacheUpdateListener.class);

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queues = CacheMqNameConstans.QUUE_NAME)
    public void receiveMessage(UpdateCacheQueueDTO message) {

        try {
            //删除缓存
            stringRedisTemplate.delete(message.getCacheKey());
            //更新缓存......
        } catch (Exception ex) {

            logger.error("CacheUpdateListener.receiveMessage error",ex.toString());
        }
    }



}

