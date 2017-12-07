package com.kingthy.service;

import com.kingthy.dto.TransferAnswerDTO;
import com.kingthy.dto.TransferDTO;
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
 * @DATE Created by 16:48 on 2017/6/23.
 * @Modified by:
 */
@FeignClient(name = "provider-payment-dubbo", fallback = UnionPayementService.HystrixClientFallbackImpl.class)
public interface UnionPayementService {

    Logger LOGGER = LoggerFactory.getLogger(UnionPayementService.class);

    /**
     * 申请银联体现
     * @param dto
     * @return
     */
    @RequestMapping(value = "/unionpay/sendTransferRequest", method = RequestMethod.POST)
    WebApiResponse<TransferAnswerDTO> sendTransferRequest(@RequestBody TransferDTO dto);


    @Component
    class HystrixClientFallbackImpl implements UnionPayementService
    {

        @Override
        public WebApiResponse<TransferAnswerDTO> sendTransferRequest(TransferDTO dto)
        {
            HystrixClientFallbackImpl.LOGGER.info("异常发生，进入fallback方法，接收的参数：DaifuDTO = {}", dto.toString());
            WebApiResponse<TransferAnswerDTO> webApiResponse = new WebApiResponse<TransferAnswerDTO>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }

    }
}
