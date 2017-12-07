package com.kingthy.service;

import com.kingthy.request.CreateGoodsReq;
import com.kingthy.response.WebApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * goodsService(商品服务消费端)
 * <p>
 * @author 赵生辉 2017年05月15日 14:53
 *
 * @version 1.0.0
 */
@FeignClient(name = "provider-goods-dubbo", fallback = GoodsService.HystrixClientFallbackImpl.class)
public interface GoodsService
{
    /**
     * 创建一个新的作品
     *
     * @param createGoodsReq
     * @return
     */
    @RequestMapping(value = "/goods/create", method = RequestMethod.POST)
    WebApiResponse<?> createGoods(@RequestBody CreateGoodsReq createGoodsReq);

    @Component
    public class HystrixClientFallbackImpl implements GoodsService
    {
        private static final Logger LOGGER = LoggerFactory.getLogger(HystrixClientFallbackImpl.class);

        @Override
        public WebApiResponse<?> createGoods(CreateGoodsReq createGoodsReq)
        {
            HystrixClientFallbackImpl.LOGGER.info("异常发生，进入fallback方法，接收的参数：bookId = {}", createGoodsReq.toString());
            WebApiResponse<String> webApiResponse = new WebApiResponse<>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }
    }
}
