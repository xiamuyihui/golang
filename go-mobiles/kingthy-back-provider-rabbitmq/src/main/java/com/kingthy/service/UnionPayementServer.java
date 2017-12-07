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
 * @author Created by likejie on 2017/6/20.
 */
@FeignClient(name = "provider-payment-dubbo", fallback = UnionPayementServer.HystrixClientFallbackImpl.class)
public interface UnionPayementServer {

    /**
     * 查询交易状态
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/unionpay/queryTranStatus/{orderId}", method = RequestMethod.GET)
    WebApiResponse<?> queryTranStatus(@PathVariable("orderId") String orderId);


    @Component
    class HystrixClientFallbackImpl implements UnionPayementServer
    {
        private static final Logger logger = LoggerFactory.getLogger(HystrixClientFallbackImpl.class);

        @Override
        public WebApiResponse<?> queryTranStatus(String orderId) {
            HystrixClientFallbackImpl.logger.info("异常发生，进入fallback方法，接收的参数：orderId = {}", orderId);
            WebApiResponse<?> webApiResponse = new WebApiResponse<String>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }
    }
}


