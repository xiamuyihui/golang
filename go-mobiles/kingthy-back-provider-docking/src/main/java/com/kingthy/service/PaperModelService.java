package com.kingthy.service;

import com.kingthy.request.PaperModelReq;
import com.kingthy.response.WebApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 20:15 on 2017/10/16.
 * @Modified by:
 */
@FeignClient(name = "provider-clothing-dubbo", fallback = PaperModelService.HystrixClientFallbackImpl.class)
public interface PaperModelService {

    /**
     * 获取纸样文件
     * @param req
     * @return
     */
    @RequestMapping(value = "/paperModel/getFilePath", method = RequestMethod.POST)
    WebApiResponse<?> getFilePath(@RequestBody PaperModelReq req);

    @Component
    class HystrixClientFallbackImpl implements PaperModelService
    {
        private static final Logger logger = LoggerFactory.getLogger(HystrixClientFallbackImpl.class);


        @Override
        public WebApiResponse<?> getFilePath(PaperModelReq req) {
            HystrixClientFallbackImpl.logger.info("异常发生，进入fallback方法，接收的参数：PaperModelReq = {}", req);
            WebApiResponse<String> webApiResponse = new WebApiResponse<>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }
    }
}
