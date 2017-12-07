package com.kingthy.service;

import com.kingthy.request.CreaterModelReq;
import com.kingthy.request.UpdateModelReq;
import com.kingthy.response.WebApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author shenghuizhao
 */
@FeignClient(name = "provider-user-dubbo",fallback = BodyModelConsumerService.HystrixClientFallbackImpl.class)
public interface BodyModelConsumerService
{

    /**
     * 获取模型的信息
     * @param token
     * @param uuid
     * @return
     */
    @RequestMapping(value = "/bodyModel/getBodyModelByUuid/{token}/{uuid}")
    public WebApiResponse<?> getBodyModelByUuid(@PathVariable(name = "parms") String token,
        @PathVariable(name = "uuid") String uuid);

    /**
     * 获取模型列表
     * @param token
     * @return
     */
    @RequestMapping(value = "/bodyModel/getBodyModelList/{token}", method = RequestMethod.GET)
    public WebApiResponse<?> getBodyModelList(@PathVariable(name = "token") String token);

    /**
     * 获取默认模型
     * @param token
     * @return
     */
    @RequestMapping(value = "/bodyModel/getBodyModelByUuid/{token}", method = RequestMethod.GET)
    public WebApiResponse<?> getDefaultBodyModel(@PathVariable(name = "token") String token);

    /**
     * 更新模型
     * @param updateModelReq
     * @return
     */
    @RequestMapping(value = "/bodyModel/update", method = RequestMethod.PUT)
    public WebApiResponse<?> updateModel(@RequestBody UpdateModelReq updateModelReq);

    /**
     * 创建模型
     * @param createrModelReq
     * @return
     */
    @RequestMapping(value = "/bodyModel/create", method = RequestMethod.POST)
    public WebApiResponse<?> createrModel(@RequestBody CreaterModelReq createrModelReq);

    /**
     * 删除模型
     * @param uuid
     * @return
     */
    @RequestMapping(value = "/delete/{uuid}", method = RequestMethod.POST)
    public WebApiResponse<?> deleteModel(@PathVariable(name = "uuid") String uuid);

    @Component
    class HystrixClientFallbackImpl implements BodyModelConsumerService
    {
        private static final Logger LOGGER = LoggerFactory.getLogger(HystrixClientFallbackImpl.class);

        @Override
        public WebApiResponse<?> updateModel(UpdateModelReq updateModelReq)
        {
            LOGGER.info("异常发生，进入fallback方法，接收的参数：", updateModelReq.toString());
            WebApiResponse<?> webApiResponse = new WebApiResponse<String>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }

        @Override
        public WebApiResponse<?> getBodyModelByUuid(String token, String uuid)
        {
            LOGGER.info("异常发生，进入fallback方法，接收的参数：", token+":"+uuid);
            WebApiResponse<?> webApiResponse = new WebApiResponse<String>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }

        @Override
        public WebApiResponse<?> getBodyModelList(String token)
        {
            LOGGER.info("异常发生，进入fallback方法，接收的参数：", token);
            WebApiResponse<?> webApiResponse = new WebApiResponse<String>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }

        @Override
        public WebApiResponse<?> getDefaultBodyModel(String token)
        {
            LOGGER.info("异常发生，进入fallback方法，接收的参数：", token);
            WebApiResponse<?> webApiResponse = new WebApiResponse<String>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }

        @Override
        public WebApiResponse<?> createrModel(CreaterModelReq createrModelReq)
        {
            LOGGER.info("异常发生，进入fallback方法，接收的参数：", createrModelReq.toString());
            WebApiResponse<?> webApiResponse = new WebApiResponse<String>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }

        @Override
        public WebApiResponse<?> deleteModel(String uuid)
        {
            LOGGER.info("异常发生，进入fallback方法，接收的参数：", uuid);
            WebApiResponse<?> webApiResponse = new WebApiResponse<String>();
            webApiResponse.setCode(-99);
            webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return webApiResponse;
        }
    }

}
