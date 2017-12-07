package com.kingthy.service;

import java.io.IOException;

import com.kingthy.response.WeixinPayRequest;

/**
 * 微信支付接口
 *
 * PaymentService
 * 
 * 潘勇 2017年2月6日 上午11:48:13
 * @author
 * @version 1.0.0
 *
 */
public interface PaymentService
{
    
    /**
     * 微信移动支付签名生成 generalWeiXinSignWap(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
     * 
     * @param weixinPayRequest
     * @return
     * @throws ParseException
     * @throws IOException
     * @throws Exception 潘勇 String
     * @exception @since 1.0.0
     */
    String generalWeiXinSignWap(WeixinPayRequest weixinPayRequest)
        throws IOException, Exception;
    
}
