package com.kingthy.service;

import com.kingthy.entity.ModelImage;
import com.kingthy.request.CreateOpusReq;
import com.kingthy.response.WebApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

import org.springframework.web.bind.annotation.*;

/**
 * OpusService(作品的接口类)
 * <p>
 * 赵生辉 2017年05月04日 17:17
 *
 * @version 1.0.0
 */
@FeignClient(name = "provider-model-dubbo", fallback = ModelService.HystrixClientFallback.class)
public interface ModelService
{

  /**
   * 创建一个新的作品
   * @param modelImage
   * @return
   */
  @RequestMapping(value = "/model/create", method = RequestMethod.POST)
  WebApiResponse<?> uploadModelImage(@RequestBody ModelImage modelImage);

  @Component
  class HystrixClientFallback implements ModelService
  {
    private static final Logger LOGGER = LoggerFactory.getLogger(HystrixClientFallback.class);


    @Override
    public WebApiResponse<?> uploadModelImage(ModelImage modelImage)
    {

      HystrixClientFallback.LOGGER.info("异常发生，进入fallback方法，接收的参数：", modelImage.toString());
      WebApiResponse<?> webApiResponse = new WebApiResponse<String>();
      webApiResponse.setCode(-99);
      webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
      return webApiResponse;
    }

  }

}