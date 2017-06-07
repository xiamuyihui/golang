package com.kingthy.service;


import com.kingthy.response.WebApiResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * 会员收获地址业务逻辑处理接口
 * 
 * likejie 2017-4-20
 * 
 * @version 1.0.0
 *
 */
@FeignClient(name = "provider-user")
public interface ReceiverService
{
    
    @RequestMapping(value = "/receiver/getReceiverList/{token}", method = RequestMethod.GET)
    WebApiResponse<?> getReceiverList(@PathVariable("token") String token);

    
}
