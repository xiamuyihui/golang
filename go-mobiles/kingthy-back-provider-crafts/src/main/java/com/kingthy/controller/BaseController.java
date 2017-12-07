package com.kingthy.controller;

import com.kingthy.cache.RedisManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name BaseController
 * @description 获取缓存
 * @create 2017/9/21
 */
public class BaseController {

    @Autowired
    private RedisManager redisManager;

    protected String getLoginerByToken(String token){
        String memberUuid = null;
        if(StringUtils.isNotEmpty(token)){
            memberUuid =redisManager.get(token);
        }
        return memberUuid;
    }
}
