package com.kingthy.service;

import com.kingthy.request.IncomeAddReq;
import com.kingthy.request.UpdateWithDrawStatusReq;
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
 * @DATE Created by 18:05 on 2017/6/14.
 * @Modified by:
 */
@FeignClient(name = "provider-income-dubbo", fallback = IncomeService.HystrixClientFallbackImpl.class)
public interface IncomeService {

    Logger LOGGER = LoggerFactory.getLogger(IncomeService.class);

    /**
     * 增加收益
     * @param req
     * @return
     */
    @RequestMapping(value = "/income/add", method = RequestMethod.POST)
    WebApiResponse<?> addIncome(@RequestBody IncomeAddReq req);

    /**
     * 会员提现
     * @param req
     * @return
     */
    @RequestMapping(value = "/income/update/withdraw", method = RequestMethod.POST)
    WebApiResponse<?> updateStatusAndOrderId(@RequestBody UpdateWithDrawStatusReq req);

    @Component
    class HystrixClientFallbackImpl implements IncomeService
    {

        @Override
        public WebApiResponse<?> addIncome(IncomeAddReq req) {

            return getStringWebApiResponse(req);
        }

        @Override
        public WebApiResponse<?> updateStatusAndOrderId(UpdateWithDrawStatusReq req) {

            return getStringWebApiResponse(req);
        }

        private WebApiResponse<String> getStringWebApiResponse(Object object) {
            IncomeService.HystrixClientFallbackImpl.LOGGER.info("异常发生，进入fallback方法，接收的参数：object = {}", object.toString());
            WebApiResponse<String> webApiResponse = new WebApiResponse<>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }
    }
}
