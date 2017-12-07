package com.kingthy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kingthy.response.WebApiResponse;
/**
 * @author:xumin
 * @Description:
 * @Date:11:40 2017/11/2
 */
@FeignClient(name = "provider-generate-sn-dubbo", fallback = SnService.HystrixClientFallbackImpl.class)
public interface SnService
{
    Logger LOGGER = LoggerFactory.getLogger(SnService.class);

    /**
     * 获取SN
     * @param type
     * @return
     */
    @RequestMapping(value = "/gererateSn/getSnByType/{type}", method = RequestMethod.GET)
    WebApiResponse<String> generateSn(@PathVariable(name = "type") String type);
    
    @Component
    class HystrixClientFallbackImpl implements SnService
    {
        
        @Override
        public WebApiResponse<String> generateSn(String type)
        {
            HystrixClientFallbackImpl.LOGGER.info("异常发生，进入fallback方法，接收的参数：type = {}", type);
            WebApiResponse<String> webApiResponse = new WebApiResponse<>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }
        
    }
}
