package com.kingthy.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.bind.annotation.*;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransOrderQueryRequest;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.google.gson.Gson;
import com.kingthy.common.Commons;
import com.kingthy.common.DateHelper;
import com.kingthy.common.EncryptOrDeCipher;
import com.kingthy.dto.AliPayRequestDTO;
import com.kingthy.dto.BizContentDTO;
import com.kingthy.dto.OrderStatusReqDTO;
import com.kingthy.dubbo.payment.service.PaymentLogDubboService;
import com.kingthy.entity.Orders;
import com.kingthy.entity.PaymentLog;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import sign.AlipayNotify;
import sign.SignUtils;


/**
 * AliPayController
 * <p>
 * 潘勇 2017年1月16日 下午2:57:28
 * @author
 * @version 1.0.0
 */
@Api
@RestController
@RequestMapping("/aliPay/")
@PropertySource("classpath:alipay.properties")
public class AliPayController
{
    private static final Logger LOG = LoggerFactory.getLogger(AliPayController.class);
    
    // 商户PID
    @Value("${ALI.PARTNER}")
    private String partner;
    
    // 商户收款账号
    @Value("${ALI.SELLER}")
    private String seller;
    
    // 商户私钥，pkcs8格式
    @Value("${ALI.RSA_PRIVATE}")
    private String rsaPrivate;
    
    // 支付宝公钥
    @Value("${ALI.RSA_PUBLIC}")
    private String rsaPublic;
    
    // 支付宝回调地址
    @Value("${ALI.NOTIFY_URL}")
    private String aliNotify;
    
    @Autowired
    private transient OrderService orderService;
    
    @Reference(version = "1.0.0", timeout = 3000)
    private PaymentLogDubboService paymentLogDubboService;
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigure()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
    @ApiOperation(value = "支付宝生成安全参数接口", notes = "")
    @RequestMapping(value = "generalPaySign", produces = {"application/json"}, method = RequestMethod.POST)
    public WebApiResponse<?> generalAliPaySign(
        @RequestBody @ApiParam(name = "aliPayRequestDTO", value = "支付参数信息", required = true) AliPayRequestDTO aliPayRequestDTO)
    {
        
        String errorMsg = "";
        String result = "";
        // 接受服务端发送的支付信息
        // 先判断此订单是否已经有付款信息 ps : 与志文确认后决定暂时不考虑
        //
        if (null != aliPayRequestDTO)
        {
            if (aliPayRequestDTO.getTotalFee() > 0)
            {
                try
                {
                    result = generalAliSignWap(aliPayRequestDTO);
                    // int paymentLogFlag = paymentsLogService.createTbPaymentsLog(result);
                    LOG.debug(">>>aliPay sign result:" + result);
                    // LOG.debug(">>>写入支付日志表" + (paymentLogFlag == 0 ? "失败" : "成功"));
                    // AlipayClient alipayClient ;
                    // alipayClient = new DefaultAlipayClient()
                    return WebApiResponse.success(result);
                }
                catch (NumberFormatException e)
                {
                    // TODO Auto-generated catch block
                    errorMsg += "类型转换异常:" + e.getMessage();
                    LOG.error("类型转换异常:" + e);
                }
                catch (UnsupportedEncodingException e)
                {
                    errorMsg += e.getMessage();
                    LOG.error("获取签名结果出错:", e);
                }
                catch (Exception e)
                {
                    errorMsg += "类型转换异常:" + e.getMessage();
                    LOG.error("类型转换异常-->" + e);
                }
                
            }
            else
            {
                errorMsg += "totalFee is null ";
            }
        }
        else
        {
            errorMsg += "反序列化时出错 aliPayRequest is null";
            LOG.error("反序列化时出错 aliPayRequest is null:");
        }
        
        return WebApiResponse.error(errorMsg);
    }
    
    @ApiOperation(value = "支付结果验证接口", notes = "")
    @PostMapping(value = "validatePayResult")
    public WebApiResponse<?> saveAliPayResult(
        @RequestBody @ApiParam(name = "aliPayReturnInfo", value = "支付结果", required = true) String aliPayReturnInfo)
    
    {
        if (StringUtils.isBlank(aliPayReturnInfo))
        {
            return WebApiResponse.error("参数不能为空");
        }
        String errorMsg = "";
        // StringUtils.substringBeforeLast(aliPayReturnInfo, "&");
        String payResult = aliPayReturnInfo;
        // payResult = StringUtils.substringBeforeLast(payResult,"&");
        LOG.debug("payResultInfo>>>" + payResult);
        String payStatus = "";
        Map<String, String> mapResult = new HashMap<>(16);
        if (StringUtils.isNotBlank(payResult))
        {
            try
            {
                Commons.parseParameters(mapResult, payResult, "UTF-8");
            }
            catch (UnsupportedEncodingException e)
            {
                LOG.error("method Commons.parseParameters exception:" + e);
            }
            // 使用支付宝的公钥进行验证
            // EncryptOrDeCipher.aes_decrypt(rsaPublic);
            String alipayPublicKey = "";
            alipayPublicKey =
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
            
            LOG.debug("mapResult>>>" + mapResult);
            boolean signVerified = false;
            // AlipaySignature.rsaCheckV1(mapResult, alipayPublicKey,
            // AlipayConstants.CHARSET_UTF8, AlipayConstants.SIGN_TYPE_RSA);
            LOG.debug("signVerified>>>" + signVerified);
            if (signVerified)
            {
                String orderSn = mapResult.get("out_trade_no");
                // errorMsg = updateOrderStatus(errorMsg, mapResult);
                
                if (StringUtils.isBlank(errorMsg))
                {
                    OrderStatusReqDTO orderStatusReqDTO = new OrderStatusReqDTO();
                    orderStatusReqDTO.setOrderSn(orderSn);
                    orderStatusReqDTO.setStatus(Orders.OrderStatus.producing.getValue());
                    WebApiResponse result = orderService.update(orderStatusReqDTO);
                    LOG.info("update order status result :" + result.toString());
                    PaymentLog paymentLog = new PaymentLog();
                    paymentLog.setSn(orderSn);
                    paymentLog.setAmount(BigDecimal.valueOf(Long.parseLong(mapResult.get("total_fee"))));
                    paymentLog.setPaymentPluginId("AliPay");
                    paymentLog.setPaymentPluginName("阿里支付");
                    paymentLog.setCreateDate(new Date());
                    paymentLogDubboService.insert(paymentLog);
                    return WebApiResponse.success("支付成功");
                }
            }
            else
            {
                errorMsg += "支付宝公钥验证异常，此次交易有风险存在被篡改的可能";
            }
        }
        else
        {
            errorMsg = getPayErrorCode(payStatus);
        }
        return WebApiResponse.error(errorMsg);
    }
    // 以下代码弃用 通过hystisx进行调用进行解耦
    /*
     * private String updateOrderStatus(String errorMsg, Map<String, String> mapResult) { try { // 存入日志信息表
     * 
     * // 从返回结果中获取订单编号 String orderSn = mapResult.get("out_trade_no"); if (StringUtils.isNotBlank(orderSn)) { // 更新订单状态
     * Order order = orderService.findBySn(orderSn); order.setOrderStatus(order.OrderStatus.pendingShipment.getValue());
     * order.setPaymentState(TbPayments.PayStatus.paid.getValue()); order.setPaymentDate(new Date());
     * order.setPaymentName(String.valueOf(Payment.PayType.aliPay.name())); order.setUpdateDate(new Date());
     * orderService.update(tbOrder, 1L); errorMsg = ""; } else { errorMsg += "支付宝支付从结果集中获取orderSn为空";
     * LOG.debug("支付宝支付从结果集中获取orderSn为空"); } } catch (Exception e) { errorMsg += "更新订单状态时出错"; LOG.error("更新订单状态时出错:" +
     * e); } return errorMsg; }
     */
    
    private String getPayErrorCode(String payStatus)
    {
        String errorMsg = "";
        switch (payStatus)
        {
            case "8000":
                errorMsg = "正在处理中";
                break;
            case "4000":
                errorMsg = "订单支付失败";
                break;
            case "6001":
                errorMsg = "用户中途取消";
                break;
            case "6002":
                errorMsg = "网络连接出错";
                break;
            default:
                break;
        }
        return errorMsg;
    }
    
    private String generalAliSignWap(AliPayRequestDTO aliPayRequest)
        throws UnsupportedEncodingException
    {
        // 生成基本信息
        String orderInfo = getOrderInfo(aliPayRequest);
        // 签名
        String sign = sign(orderInfo);
        // 构造返回给服务端的数据
        /**
         * 仅需对sign 做URL编码
         */
        sign = URLEncoder.encode(sign, "UTF-8");
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
        return payInfo;
    }
    
    /**
     * 支付宝创建订单支付信息 getOrderInfo(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
     *
     * @param aliPayRequest
     * @return String
     * @throws @since 1.0.0
     * @author pany
     */
    private String getOrderInfo(AliPayRequestDTO aliPayRequest)
    {
        StringBuffer orderInfoBuf = new StringBuffer();
        // 签约合作者身份ID
        orderInfoBuf.append("partner=" + "\"" + EncryptOrDeCipher.aes_decrypt(partner) + "\"");
        // 签约卖家支付宝账号
        orderInfoBuf.append("&seller_id=" + "\"" + EncryptOrDeCipher.aes_decrypt(seller) + "\"");
        // 商户网站唯一订单号
        orderInfoBuf.append("&out_trade_no=" + "\"" + aliPayRequest.getOutTradeNo() + "\"");
        // 商品名称
        orderInfoBuf.append("&subject=" + "\"" + aliPayRequest.getSubject() + "\"");
        // 商品详情
        orderInfoBuf.append("&body=" + "\"" + aliPayRequest.getBody() + "\"");
        // 商品金额
        orderInfoBuf.append("&total_amount=" + "\"" + aliPayRequest.getTotalFee() + "\"");
        // 服务器异步通知页面路径
        orderInfoBuf.append("&notify_url=" + "\"" + aliNotify + "\"");
        // 服务接口名称， 固定值
        orderInfoBuf.append("&service=\"mobile.securitypay.pay\"");
        // 支付类型， 固定值
        orderInfoBuf.append("&payment_type=\"1\"");
        // 参数编码， 固定值
        orderInfoBuf.append("&_input_charset=\"utf-8\"");
        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfoBuf.append("&it_b_pay=\"" + DateHelper.getTomorrowDate(1) + "\"");
        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";
        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfoBuf.append("&return_url=\"m.alipay.com\"");
        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";
        return orderInfoBuf.toString();
    }
    
    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content)
    {
        return SignUtils.sign(content, EncryptOrDeCipher.aes_decrypt(rsaPrivate));
    }
    
    /**
     * get the sign type we use. 获取签名方式
     */
    private static String getSignType()
    {
        return "sign_type=\"RSA\"";
    }
    
    public boolean verifyAliPayResult(Map<String, String> mapResult)
    {
        String alipayPublicKey = EncryptOrDeCipher.aes_decrypt(rsaPublic);
        String parnterId = EncryptOrDeCipher.aes_decrypt(partner);
        return AlipayNotify.verify(mapResult, parnterId, alipayPublicKey);
    }
    
    /**
     * 提现到支付保
     */
    @ApiOperation(value = "提现到支付保", notes = "")
    @RequestMapping(value = "transfer", method = RequestMethod.POST)
    public WebApiResponse<?> withdrawCash(
        @RequestBody @ApiParam(name = "bizContentDTO", value = "提现到支付保", required = true) BizContentDTO bizContentDTO)
    {
        
        AlipayFundTransToaccountTransferResponse response = null;
        
        try
        {
            
            String url = "https://openapi.alipay.com/gateway.do";
            String appId = "2016033101256223";
            String appPrivateKey = EncryptOrDeCipher.aes_decrypt(rsaPrivate);
            String format = "json";
            String charset = "UTF-8";
            String alipayPublicKey = EncryptOrDeCipher.aes_decrypt(rsaPublic);
            String signType = "RSA2";
            
            AlipayClient alipayClient =
                new DefaultAlipayClient(url, appId, appPrivateKey, format, charset, alipayPublicKey, signType);
            
            AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
            if (null == bizContentDTO)
            {
                bizContentDTO = new BizContentDTO();
            }
            bizContentDTO.setAmount("0.01");
            bizContentDTO.setOut_biz_no("12313");
            bizContentDTO.setPayee_account("18682358815");
            bizContentDTO.setPayee_type("ALIPAY_LOGONID");
            bizContentDTO.setPayee_real_name("舒绍林");
            bizContentDTO.setRemark("测试一下");
            
            Gson gson = new Gson();
            request.setBizContent(gson.toJson(bizContentDTO).toString());
            
            /*
             * request.setBizContent("{" + "    \"out_biz_no\":\"3142321423432\"," +
             * "    \"payee_type\":\"ALIPAY_LOGONID\"," + "    \"payee_account\":\"abc@sina.com\"," +
             * "    \"amount\":\"12.23\"," + "    \"payer_show_name\":\"上海交通卡退款\"," + "    \"payee_real_name\":\"张三\","
             * + "    \"remark\":\"转账备注\"," + "  }");
             */
            
            // AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
            
            response = alipayClient.execute(request);
            
            if (response.isSuccess())
            {
                System.out.println("调用成功 code: " + response.getSubCode());

//                subCode值
                String subCode = "10000";
                if (subCode.equals(response.getSubCode()))
                {
                    
                }
            }
            else
            {
                System.out.println("调用失败");
            }
            
        }
        catch (Exception e)
        {
            LOG.error(e.toString());
        }
        
        return WebApiResponse.success(response);
    }
    
    @ApiOperation(value = "查询提现结果", notes = "")
    @RequestMapping(value = "transQuery/{outBizNo}/{orderId}", method = RequestMethod.GET)
    public WebApiResponse<?> transQuery(@PathVariable @ApiParam(value = "订单号") String outBizNo,
        @PathVariable @ApiParam(value = "支付保转账单据号") String orderId)
    {
        
        AlipayFundTransOrderQueryResponse response = null;
        
        try
        {
            
            String url = "https://openapi.alipay.com/gateway.do";
            String appId = "2016033101256223";
            String appPrivateKey = EncryptOrDeCipher.aes_decrypt(rsaPrivate);
            String format = "json";
            String charset = "UTF-8";
            String alipayPublicKey = EncryptOrDeCipher.aes_decrypt(rsaPublic);
            String signType = "RSA2";
            
            AlipayClient alipayClient =
                new DefaultAlipayClient(url, appId, appPrivateKey, format, charset, alipayPublicKey, signType);
            AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
            
            Map<String, String> paraMap = new HashMap<>(2);
            paraMap.put("out_biz_no", outBizNo);
            paraMap.put("order_id", orderId);
            
            Gson gson = new Gson();
            request.setBizContent(gson.toJson(paraMap).toString());
            
            /*
             * request.setBizContent("{" + "\"out_biz_no\":\"3142321423432\"," +
             * "\"order_id\":\"20160627110070001502260006780837\"" + "  }");
             */
            // AlipayFundTransOrderQueryResponse response = alipayClient.execute(request);
            
            response = alipayClient.execute(request);
            
            if (response.isSuccess())
            {
                System.out.println("调用成功");
            }
            else
            {
                System.out.println("调用失败");
            }
        }
        catch (Exception e)
        {
            LOG.error(e.toString());
        }
        
        return WebApiResponse.success(response);
    }
    
}
