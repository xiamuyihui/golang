package com.kingthy.service;

import com.kingthy.dto.IfoOrderDTO;
import com.kingthy.dto.IfoOrderDetailBomDTO;
import com.kingthy.dto.IfoOrderStyleFileDTO;
import com.kingthy.dto.IfoStitchingStyleDTO;
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
 * @DATE Created by 15:06 on 2017/10/12.
 * @Modified by:
 */
@FeignClient(name = "provider-docking-dubbo", fallback = IfoOrderInfoService.HystrixClientFallbackImpl.class)
public interface IfoOrderInfoService
{

    /**
     * 创建CIPP订单
     * @param req
     * @return
     */
    @RequestMapping(value = "/ifo/order/create", method = RequestMethod.POST)
    WebApiResponse<?> createIfoOrder(@RequestBody IfoOrderDTO req);

    /**
     * 创建CIPP订单Bom
     * @param req
     * @return
     */
    @RequestMapping(value = "/ifo/order/bom", method = RequestMethod.POST)
    WebApiResponse<?> createOrderItemBom(@RequestBody IfoOrderDetailBomDTO req);

    /**
     * 创建CIPP订单款式
     * @param req
     * @return
     */
    @RequestMapping(value = "/ifo/order/style", method = RequestMethod.POST)
    WebApiResponse<?> createOrderStyleFile(@RequestBody IfoOrderStyleFileDTO req);

    /**
     * 创建CIPP订单缝合关系
     * @param req
     * @return
     */
    @RequestMapping(value = "/ifo/order/stitching", method = RequestMethod.POST)
    WebApiResponse<?> createStitchingStyle(@RequestBody IfoStitchingStyleDTO req);

    @Component
    class HystrixClientFallbackImpl implements IfoOrderInfoService
    {
        private static final Logger logger = LoggerFactory.getLogger(HystrixClientFallbackImpl.class);


        @Override
        public WebApiResponse<?> createIfoOrder(IfoOrderDTO req) {
            HystrixClientFallbackImpl.logger.info("异常发生，进入fallback方法，接收的参数：IfoOrderDTO = {}", req.toString());
            WebApiResponse<?> webApiResponse = new WebApiResponse<String>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }

        @Override
        public WebApiResponse<?> createOrderItemBom(IfoOrderDetailBomDTO req) {
            HystrixClientFallbackImpl.logger.info("异常发生，进入fallback方法，接收的参数：IfoOrderDetailBomDTO = {}", req.toString());
            WebApiResponse<?> webApiResponse = new WebApiResponse<String>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }

        @Override
        public WebApiResponse<?> createOrderStyleFile(IfoOrderStyleFileDTO req) {
            HystrixClientFallbackImpl.logger.info("异常发生，进入fallback方法，接收的参数：IfoOrderStyleFileDTO = {}", req.toString());
            WebApiResponse<?> webApiResponse = new WebApiResponse<String>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }

        @Override
        public WebApiResponse<?> createStitchingStyle(IfoStitchingStyleDTO req) {
            HystrixClientFallbackImpl.logger.info("异常发生，进入fallback方法，接收的参数：IfoStitchingStyleDTO = {}", req.toString());
            WebApiResponse<?> webApiResponse = new WebApiResponse<String>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }
    }
}
