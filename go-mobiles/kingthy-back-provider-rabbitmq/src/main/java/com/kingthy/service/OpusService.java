package com.kingthy.service;

import com.kingthy.request.CreateOpusReq;
import com.kingthy.request.QueryOpusListReq;
import com.kingthy.response.WebApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * OpusService(描述其作用)
 * <p>
 * @author 赵生辉 2017年08月23日 14:18
 *
 * @version 1.0.0
 */
@FeignClient(name = "provider-opus-dubbo", fallback = OpusService.HystrixClientFallbackImpl.class)
public interface OpusService
{

    /**
     * 创建一个新的作品
     * @param createOpusReq
     * @return
     */
    @RequestMapping(value = "/opus/create", method = RequestMethod.POST)
    WebApiResponse<?> createOpus(@RequestBody CreateOpusReq createOpusReq);

    /**
     * 查询我的作品列表
     * @param queryOpusListReq
     * @return
     */
    @RequestMapping(value = "/opus/queryOpusList", method = RequestMethod.POST)
    WebApiResponse<?> queryOpusList(@RequestBody QueryOpusListReq queryOpusListReq);

    /**
     * 查询作品详情
     * @param uuid
     * @return
     */
    @RequestMapping(value = "/opus/{uuid}", method = RequestMethod.GET)
    WebApiResponse<?> queryOpusInfo(@PathVariable(name ="uuid") String uuid);

    /**
     * 删除指定的作品
     * @param list
     * @return
     */
    @RequestMapping(value = "/opus",method = RequestMethod.DELETE)
    WebApiResponse<?> deleteOpus(List<String> list);

    /**
     * 修改作品的状态
     * @param status
     * @param uuid
     * @return
     */
    @RequestMapping(value = "/opus/{uuid}/{status}",method = RequestMethod.PUT)
    WebApiResponse<?> updateOpus(@PathVariable(name ="uuid") String uuid,@PathVariable(name ="status") Integer status);


    /**
     * 作品金额
     * @param uuid
     * @return
     */
    @RequestMapping(value = "/opus/amount/{uuid}", method = RequestMethod.GET)
    WebApiResponse<?> opusAmount(@PathVariable(name ="uuid") String uuid);

    @Component
    class HystrixClientFallbackImpl implements OpusService
    {
        private static final Logger LOGGER = LoggerFactory.getLogger(HystrixClientFallbackImpl.class);


        @Override
        public WebApiResponse<?> createOpus(CreateOpusReq createOpusReq)
        {
            HystrixClientFallbackImpl.LOGGER.info("异常发生，进入fallback方法，接收的参数：", createOpusReq.toString());
            WebApiResponse<String> webApiResponse = new WebApiResponse<>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }

        @Override
        public WebApiResponse<?> queryOpusList(QueryOpusListReq queryOpusListReq)
        {
            HystrixClientFallbackImpl.LOGGER.info("异常发生，进入fallback方法，接收的参数：", queryOpusListReq.toString());
            WebApiResponse<?> webApiResponse = new WebApiResponse<String>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }

        @Override
        public WebApiResponse<?> queryOpusInfo(String uuid)
        {
            HystrixClientFallbackImpl.LOGGER.info("异常发生，进入fallback方法，接收的参数：", uuid);
            WebApiResponse<?> webApiResponse = new WebApiResponse<String>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }

        @Override
        public WebApiResponse<?> deleteOpus(List<String> list)
        {
            HystrixClientFallbackImpl.LOGGER.info("异常发生，进入fallback方法，接收的参数：", list.toString());
            WebApiResponse<?> webApiResponse = new WebApiResponse<String>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }

        @Override
        public WebApiResponse<?> updateOpus(String uuid,Integer status)
        {
            HystrixClientFallbackImpl.LOGGER.info("异常发生，进入fallback方法，接收的参数：", uuid +":"+ status);
            WebApiResponse<?> webApiResponse = new WebApiResponse<String>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }

        @Override
        public WebApiResponse<?> opusAmount(String uuid)
        {
            HystrixClientFallbackImpl.LOGGER.info("异常发生，进入fallback方法，接收的参数：", uuid);
            WebApiResponse<?> webApiResponse = new WebApiResponse<String>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }

        public WebApiResponse<?> fallBack(String data){
            HystrixClientFallbackImpl.LOGGER.info("异常发生，进入fallback方法，接收的参数：", data);
            WebApiResponse<?> webApiResponse = new WebApiResponse<String>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }

    }
}
