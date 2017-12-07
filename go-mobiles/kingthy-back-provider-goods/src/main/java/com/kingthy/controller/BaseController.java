package com.kingthy.controller;

import com.kingthy.common.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.http.HttpServletRequest;

public abstract class BaseController
{

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    protected String getMemberByToken(String token)
    {
        return stringRedisTemplate.opsForValue().get(token);
    }

    protected String getMemberByToken()
    {
        return getMemberByToken(getToken());
    }

    protected String getToken()
    {
        return request.getHeader(CommonUtils.REQUEST_HEADER_PARAMETER);
    }

}
