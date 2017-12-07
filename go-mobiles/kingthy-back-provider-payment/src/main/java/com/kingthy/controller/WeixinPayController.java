package com.kingthy.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kingthy.response.WebApiResponse;
import com.kingthy.response.WeixinPayRequest;
import com.kingthy.service.impl.PaymentServiceImpl;

import io.swagger.annotations.Api;

/**
 * 
 * 微信支付 WeixinPayController
 * 
 * 潘勇 2017年4月18日 下午10:41:58
 * @author
 * @version 1.0.0
 *
 */

@Api
@RestController
@RequestMapping("/wechartPay/")
public class WeixinPayController
{
    
    private static final Logger LOG = LoggerFactory.getLogger(WeixinPayController.class);
    
    @Autowired
    private transient PaymentServiceImpl paymentService;
    
    /**
     * 获取签名 weiXinPay(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
     * 
     * @param weixinPayRequest
     * @return
     * @throws ParseException
     * @throws IOException 潘勇 WebApiResponse<String>
     * @exception @since 1.0.0
     */
    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public @ResponseBody WebApiResponse<String> weiXinPay(@RequestBody WeixinPayRequest weixinPayRequest)
        throws Exception, IOException
    {
        String errorCode = "999";
        String errorMsg = "";
        String payResult = "";
        try
        {
            payResult = paymentService.generalWeiXinSignWap(weixinPayRequest);
            return WebApiResponse.success(payResult);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            errorCode = "5503";
            errorMsg += e.getMessage();
            LOG.error("===>生成签名结果出错:" + e);
            return WebApiResponse.error(errorMsg);
        }
    }
    
}
