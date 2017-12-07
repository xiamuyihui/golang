package com.kingthy.remoteservice;

import com.kingthy.common.CommonUtils;
import com.kingthy.dto.IncomeBalanceDTO;
import com.kingthy.response.WebApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 *
 * @author  by likejie on 2017/6/28.
 */
@FeignClient(name = "provider-income-dubbo", fallback = IncomeService.HystrixClientFallbackImpl.class)
public interface IncomeService {

    Logger LOGGER = LoggerFactory.getLogger(IncomeService.class);
    /**
     * 查询绑定的银行卡
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/income/bank", method = RequestMethod.POST)
    WebApiResponse<IncomeBalanceDTO> queryBankInfo(@RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token);

    @Component
    class HystrixClientFallbackImpl implements IncomeService
    {

        @Override
        public WebApiResponse<IncomeBalanceDTO> queryBankInfo(String token) {
            HystrixClientFallbackImpl.LOGGER.info("异常发生，进入fallback方法，接收的参数：token= {}", token);
            WebApiResponse<IncomeBalanceDTO> webApiResponse = new WebApiResponse<>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }
    }
}
