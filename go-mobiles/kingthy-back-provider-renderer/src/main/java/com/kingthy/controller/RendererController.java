/**
 * 系统项目名称
 * com.kingthy.controller
 * RendererController.java
 * 
 * 2017年5月17日-下午5:23:48
 *  2017金昔科技有限公司-版权所有
 *
 */
package com.kingthy.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kingthy.dto.RequestDTO;
import com.kingthy.response.WebApiResponse;
import com.kingthy.response.WebApiResponse.ResponseMsg;
import com.kingthy.service.RendererService;

import io.swagger.annotations.Api;

/**
 *
 * RendererController
 * 
 * 李克杰 2017年5月17日 下午5:23:48
 * 
 * @version 1.0.0
 *
 */
@Api
@RestController
@RequestMapping("/renderer")
public class RendererController
{
    
    private static final Logger LOG = LoggerFactory.getLogger(RendererController.class);
    
    @Autowired
    RendererService rendererService;
    
    @Autowired
    protected StringRedisTemplate stringRedisTemplate;
    
    /**
     * 从token中获取登录用户
     * 
     */
    protected String getLoginerByToken(String token)
    {
        
        String memberUuid = null;
        if (StringUtils.isNotBlank(token))
        {
            memberUuid = stringRedisTemplate.opsForValue().get(token);
            /*
             * if (StringUtils.isNotBlank(memberInfo)) { memberUuid =
             * com.kingthy.common.KryoSerializeUtils.deserializationObject(memberInfo, String.class); }
             */
        }
        return memberUuid;
    }
    
    @PostMapping("/test")
    public WebApiResponse<?> test(@RequestBody RequestDTO data)
    {
        return WebApiResponse.success();
    }
    
    @PostMapping("/generate3Dgraphs")
    public WebApiResponse<?> generate3Dgraphs(@RequestBody RequestDTO dto)
    {
        try
        {
            // 从缓存获取token
            String memberUuid = getLoginerByToken(dto.getToken());
            if (StringUtils.isBlank(memberUuid))
            {
                return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
            }
            String url = rendererService.generate3Dgraphs(memberUuid, dto.getCurrentTimespan(), dto.getFileId());
            return WebApiResponse.success(url);
        }
        catch (Exception e)
        {
            LOG.error("RendererController.generate3Dgraphs error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }
}
