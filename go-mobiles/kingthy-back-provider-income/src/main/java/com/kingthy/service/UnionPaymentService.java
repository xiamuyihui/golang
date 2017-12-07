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
 * @author xumin
 * @Description:
 * @DATE Created by 16:48 on 2017/6/23.
 * @Modified by:
 */
@FeignClient(name = "provider-payment-dubbo", fallback = UnionPaymentService.HystrixClientFallbackImpl.class)
public interface UnionPaymentService {

    Logger LOGGER = LoggerFactory.getLogger(UnionPaymentService.class);

    /**
     *  查询提现结果
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/unionpay/queryTranStatus/{orderId}", method = RequestMethod.GET)
    WebApiResponse<?> queryTranStatus(@PathVariable("orderId") String orderId);


    @Component
    class HystrixClientFallbackImpl implements UnionPaymentService
    {

        @Override
        public WebApiResponse<String> queryTranStatus(String orderId)
        {
            HystrixClientFallbackImpl.LOGGER.info("异常发生，进入fallback方法，接收的参数：orderId = {}", orderId);
            WebApiResponse<String> webApiResponse = new WebApiResponse<>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }

    }
}
