package com.kingthy.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kingthy.request.CreateOpusReq;
import com.kingthy.request.QueryOpusListReq;
import com.kingthy.response.WebApiResponse;

/**
 * OpusService(描述其作用)
 * <p>
 * 赵生辉 2017年05月15日 10:29
 *
 * @version 1.0.0
 */
@FeignClient(name = "provider-opus-dubbo", fallback = OpusService.HystrixClientFallback.class)
public interface OpusService
{
    
    String TIPS = "无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！";
    
    /**
     * 创建一个新的作品
     *
     * @param createOpusReq
     * @return
     */
    @RequestMapping(value = "/opus/create", method = RequestMethod.POST)
    WebApiResponse createOpus(@RequestBody CreateOpusReq createOpusReq);
    
    /**
     * 查询我的作品列表
     *
     * @param queryOpusListReq
     * @return
     */
    @RequestMapping(value = "/opus/queryOpusList", method = RequestMethod.POST)
    WebApiResponse queryOpusList(@RequestBody QueryOpusListReq queryOpusListReq);
    
    /**
     * 查询作品详情
     *
     * @param uuid
     * @return
     */
    @RequestMapping(value = "/opus/{uuid}", method = RequestMethod.GET)
    WebApiResponse queryOpusInfo(@PathVariable(name = "uuid") String uuid);
    
    /**
     * 查询指定作品
     *
     * @param uuid
     * @return
     */
    @RequestMapping(value = "/opus/query/{uuid}", method = RequestMethod.GET)
    WebApiResponse queryOpus(@PathVariable(name = "uuid") String uuid);
    
    /**
     * 删除指定的作品
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/opus", method = RequestMethod.DELETE)
    WebApiResponse deleteOpus(List<String> list);
    
    /**
     * 修改作品的状态
     * 
     * @param
     * @return
     */
    @RequestMapping(value = "/opus/{uuid}/{status}", method = RequestMethod.PUT)
    WebApiResponse updateOpus(@PathVariable(name = "uuid") String uuid, @PathVariable(name = "status") Integer status);
    
    @Component
    class HystrixClientFallback implements OpusService
    {
        private static final Logger LOGGER = LoggerFactory.getLogger(HystrixClientFallback.class);
        
        @Override
        public WebApiResponse<?> createOpus(CreateOpusReq createOpusReq)
        {
            
            HystrixClientFallback.LOGGER.info("异常发生，进入fallback方法，接收的参数：bookId = {}", createOpusReq.toString());
            WebApiResponse<?> webApiResponse = new WebApiResponse<>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage(TIPS);
            return webApiResponse;
        }
        
        @Override
        public WebApiResponse<?> queryOpusList(QueryOpusListReq queryOpusListReq)
        {
            HystrixClientFallback.LOGGER.info("异常发生，进入fallback方法，接收的参数：bookId = {}", queryOpusListReq.toString());
            WebApiResponse<?> webApiResponse = new WebApiResponse<>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage(TIPS);
            return webApiResponse;
        }
        
        @Override
        public WebApiResponse<?> queryOpusInfo(String uuid)
        {
            HystrixClientFallback.LOGGER.info("异常发生，进入fallback方法，接收的参数：bookId = {}", uuid);
            WebApiResponse<?> webApiResponse = new WebApiResponse<>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage(TIPS);
            return webApiResponse;
        }
        
        @Override
        public WebApiResponse<?> queryOpus(String uuid)
        {
            HystrixClientFallback.LOGGER.info("异常发生，进入fallback方法，接收的参数：bookId = {}", uuid);
            WebApiResponse<?> webApiResponse = new WebApiResponse<>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage(TIPS);
            return webApiResponse;
        }
        
        @Override
        public WebApiResponse<?> deleteOpus(List<String> list)
        {
            HystrixClientFallback.LOGGER.info("异常发生，进入fallback方法，接收的参数：bookId = {}", list.toString());
            WebApiResponse<?> webApiResponse = new WebApiResponse<>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage(TIPS);
            return webApiResponse;
        }
        
        @Override
        public WebApiResponse<?> updateOpus(String uuid, Integer status)
        {
            HystrixClientFallback.LOGGER.info("异常发生，进入fallback方法，接收的参数：bookId = {}", uuid + ":" + status);
            WebApiResponse<?> webApiResponse = new WebApiResponse<>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage(TIPS);
            return webApiResponse;
        }
    }
}