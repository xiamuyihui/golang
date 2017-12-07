package com.kingthy.service;

import com.kingthy.dto.OrderStatusReqDTO;
import com.kingthy.response.WebApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author
 */
@FeignClient(name = "provider-order-dubbo", fallback = OrderService.HystrixClientFallbackImpl.class)
public interface OrderService {
    Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    /**
     * 更新订单状态
     * @param orderStatusReqDTO
     * @return
     */
    @RequestMapping(value = "/order/update", method = RequestMethod.POST)
    public WebApiResponse<String> update(@RequestBody OrderStatusReqDTO orderStatusReqDTO);

    @Component
    class HystrixClientFallbackImpl implements OrderService
    {

        @Override
        public WebApiResponse<String> update(OrderStatusReqDTO orderStatusReqDTO)
        {
            HystrixClientFallbackImpl.LOGGER.info("异常发生，进入fallback方法，接收的参数：orderSn = {}", orderStatusReqDTO.toString());
            WebApiResponse<String> webApiResponse = new WebApiResponse<String>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }

    }
}
