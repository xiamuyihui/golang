package com.kingthy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

import com.kingthy.response.WebApiResponse;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * @author:xumin
 * @Description:
 * @Date:11:39 2017/11/2
 */
@FeignClient(name = "redis-service-dubbo", fallback = MemberService.HystrixClientFallbackImpl.class)
public interface MemberService
{
    Logger LOGGER = LoggerFactory.getLogger(MemberService.class);

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    @RequestMapping(value = "/redis/getUserInfo/{token}", method = RequestMethod.GET)
    public WebApiResponse<String> getUserInfoFromRedis(@RequestHeader(name = "Authorization")  String token);
    
    @Component
    class HystrixClientFallbackImpl implements MemberService
    {
        
        @Override
        public WebApiResponse<String> getUserInfoFromRedis(String token)
        {
            HystrixClientFallbackImpl.LOGGER.info("异常发生，进入fallback方法，接收的参数：token = {}", token);
            WebApiResponse<String> webApiResponse = new WebApiResponse<>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }
        
    }
}
