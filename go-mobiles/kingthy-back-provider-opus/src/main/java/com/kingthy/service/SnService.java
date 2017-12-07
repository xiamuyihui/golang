package com.kingthy.service;

import com.kingthy.response.WebApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @author shenghuizhao
 */
@FeignClient(name = "provider-generate-sn-dubbo", fallback = SnService.HystrixClientFallbackImpl.class)
public interface SnService
{
    Logger LOGGER = LoggerFactory.getLogger(SnService.class);

    /**
     * 生成sn方法
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
            HystrixClientFallbackImpl.LOGGER.info("异常发生，进入fallback方法，接收的参数：userDTO = {}", type.toString());
            WebApiResponse<String> webApiResponse = new WebApiResponse<String>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }

    }
}
