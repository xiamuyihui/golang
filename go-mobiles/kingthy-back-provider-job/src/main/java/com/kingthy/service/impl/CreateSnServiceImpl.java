package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.common.CommonUtils;
import com.kingthy.dubbo.order.service.SnDubboService;
import com.kingthy.service.CreateSnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 14:38 on 2017/11/21.
 * @Modified by:
 */
@Service
public class CreateSnServiceImpl implements CreateSnService
{
    @Reference(version ="1.0.0")
    private SnDubboService snDubboService;

    //订单数量
    @Value("${sn.number}")
    private Integer snNumber;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public synchronized void saveSnToRedis(String key, String type){

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

//        String key = CommonUtils.REIDS_ORDER_SN_KEY + ":" + sdf.format(new Date());

        if (stringRedisTemplate.opsForList().size(key) >= snNumber){
            return;
        }

        for (int m = 0; m < snNumber; m++){
            stringRedisTemplate.opsForList().leftPush(key, snDubboService.generateSn(type).getData());
        }

        //过期时间
        if (stringRedisTemplate.hasKey(key)){
            stringRedisTemplate.expire(key, 60 * 60 * 24, TimeUnit.SECONDS);
        }

    }

    @Override
    public synchronized void checkSnSize(String key, String type){

        Long size = stringRedisTemplate.opsForList().size(key);

        if (size <= snNumber / 3){
            saveSnToRedis(key, type);
        }
    }
}
