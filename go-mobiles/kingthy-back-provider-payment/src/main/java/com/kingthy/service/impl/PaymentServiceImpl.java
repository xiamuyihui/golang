package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.common.AmountUtils;
import com.kingthy.common.Commons;
import com.kingthy.common.DateHelper;
import com.kingthy.common.EncryptOrDeCipher;
import com.kingthy.dubbo.payment.service.PaymentLogDubboService;
import com.kingthy.entity.PaymentLog;
import com.kingthy.response.WeixinPayRequest;
import com.kingthy.service.PaymentService;
import com.kingthy.util.MD5Util;
import com.kingthy.util.XmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 订单支付相关信息，主要包含了微信支付以及支付宝支付的处理
 *
 * @author pany 2016年4月21日 下午2:54:20
 * @version 1.0.0
 */
@Service(value = "paymentService")
@PropertySource("classpath:alipay.properties")
public class PaymentServiceImpl implements PaymentService
{

    // 微信统一下单地址
    @Value("${WEIXIN_PAY}")
    private String weiXinPayUrl;

    // 商户号
    @Value("${MCH_ID}")
    private String mchId;

    // 应用ID
    @Value("${APP_ID}")
    private String appId;

    // 回调地址
    @Value("${NOTIFY_URL}")
    private String notifyUrl;

    // 私钥
    @Value("${PRIVATE_KEY}")
    private String privateKey;

    private static final Logger LOG = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private static final int TTL_TIME = 30 * 60 * 1000;

    /**
     * 格式化当前系统日期
     */
    private final SimpleDateFormat dateFm = new SimpleDateFormat("yyyyMMddHHmmss");

    @Autowired
    RestTemplate restTemplate;

    @Reference(version = "1.0.0", timeout = 3000)
    private PaymentLogDubboService paymentLogDubboService;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigure()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * 微信移动支付签名生成 generalWeiXinSignWap(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
     *
     * @return String
     * @throws IOException
     * @throws Exception
     * @throws @since      1.0.0
     * @author pany
     */
    @Override
    public String generalWeiXinSignWap(WeixinPayRequest weixinPayRequest)
        throws IOException, Exception
    {
        String appId = EncryptOrDeCipher.aes_decrypt(this.appId);
        String aesDecrypt = EncryptOrDeCipher.aes_decrypt(mchId);
        SortedMap<Object, Object> sortedMap = sortedParameter(weixinPayRequest);
        String preSign = "";
        try
        {
            preSign = createSign(sortedMap, true).get("sign");
        }
        catch (Exception e1)
        {
            LOG.error("获取预支付签名失败：" + e1);
        }
        sortedMap.put("sign", preSign);
        String xmlInfo = generateXmlInfo(sortedMap);
        LinkedMultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<String, String>();
        multiValueMap.add("xmlInfo", xmlInfo);
        LOG.info("xmlInfo result:" + xmlInfo);
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/xml; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> requestEntity = new HttpEntity<String>(xmlInfo, headers);
        String result = restTemplate.postForObject(weiXinPayUrl, requestEntity, String.class);
        LOG.info("weixinPay result:" + result);
        String prepayId = "";
        try
        {
            prepayId = XmlUtil.getXmlAttributeValue(result, "prepay_id");
        }
        catch (XPathExpressionException e)
        {
            LOG.error("从xml中获取预支付编码失败" + e);
        }
        // 重新构造数据
        sortedMap.clear();
        xmlInfo = "";
        result = "";
        sortedMap.put("appid", appId);
        sortedMap.put("partnerid", aesDecrypt);
        sortedMap.put("prepayid", prepayId);
        LOG.info("prepayId====>>>" + prepayId);
        sortedMap.put("package", "Sign=WXPay");
        sortedMap.put("noncestr", Commons.generateUUID());
        sortedMap.put("timestamp", (int)(System.currentTimeMillis() / 1000));
        Map<String, String> map = createSign(sortedMap, true);
        String paySign = map.get("sign");
        String paramResult = map.get("parmStr");
        paramResult = StringUtils.substringBeforeLast(paramResult, "&") + "&sign=" + paySign;
        LOG.info("paramResult====>>>" + paramResult);
        try
        {
            PaymentLog paymentLog = new PaymentLog();
            paymentLog.setSn((String)sortedMap.get("out_trade_no"));
            paymentLog.setAmount(BigDecimal.valueOf(Double.parseDouble((String)sortedMap.get("total_fee"))));
            paymentLog.setPaymentPluginId("WxPay");
            paymentLog.setPaymentPluginName("微信支付");
            paymentLog.setCreateDate(new Date());
            paymentLogDubboService.insert(paymentLog);
        }
        catch (Exception e)
        {
            LOG.error("微信支付日志出错 "+e.toString());
        }

        return paramResult;
    }

    /**
     * 微信支付 构造所需签名的参数 sortedParameter(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
     *
     * @return SortedMap<Object,Object>
     * @throws Exception
     * @throws @since    1.0.0
     * @author pany
     */
    private SortedMap<Object, Object> sortedParameter(WeixinPayRequest weixinPayRequest)
        throws Exception
    {

        String appID = EncryptOrDeCipher.aes_decrypt(appId);
        String partnerID = EncryptOrDeCipher.aes_decrypt(mchId);
        SortedMap<Object, Object> sortedMap = new TreeMap<Object, Object>();
        String nonceStr = Commons.generateUUID();
        String body = weixinPayRequest.getBody();
        String attach = weixinPayRequest.getAttach();
        System.out.println(DateHelper.getCurrentlyDate().toString());

        String timeStart = dateFm.format(new java.util.Date());
        String timeExpire = dateFm.format(new Date(System.currentTimeMillis() + TTL_TIME));
        String feeType = "CNY";
        String totalFee = weixinPayRequest.getTotal_fee();
        String spbillCreateIp = weixinPayRequest.getSpbill_create_ip();
        String tradeType = "APP";
        sortedMap.put("appid", appID);
        sortedMap.put("mch_id", partnerID);
        sortedMap.put("attach", attach);
        sortedMap.put("nonce_str", nonceStr);
        sortedMap.put("body", body);
        sortedMap.put("out_trade_no", weixinPayRequest.getOut_trade_no());
        sortedMap.put("total_fee", AmountUtils.changeY2F(totalFee));
        sortedMap.put("fee_type", feeType);
        sortedMap.put("spbill_create_ip", spbillCreateIp);
        sortedMap.put("notify_url", notifyUrl);
        sortedMap.put("trade_type", tradeType);
        sortedMap.put("time_start", timeStart);
        sortedMap.put("time_expire", timeExpire);
        return sortedMap;
    }

    /**
     * 生成微信支付平台所需的xml信息 generateXmlInfo(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
     *
     * @param param
     * @return String
     * @throws @since 1.0.0
     * @author pany
     */
    public static String generateXmlInfo(SortedMap<Object, Object> param)
    {
        Element root = new Element("xml");
        Document document = new Document(root);
        Element key = null;
        for (Map.Entry<Object, Object> entry : param.entrySet())
        {
            key = new Element(entry.getKey().toString());
            key.setText(entry.getValue().toString());
            root.addContent(key);
        }
        try
        {
            XMLOutputter xmlOutputter = new XMLOutputter();
            String payInfoXml = xmlOutputter.outputString(document);
            return payInfoXml;

        }
        catch (Exception e)
        {
            LOG.error("map 转 xml 出错:" + e);
        }
        return root.toString();
    }

    /**
     * 签名验证 createSign(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
     *
     * @param parameters
     * @param needKey
     * @return Map<String,String>
     * @throws @since 1.0.0
     * @author pany
     */
    public Map<String, String> createSign(SortedMap<Object, Object> parameters, boolean needKey)
    {
        StringBuffer sb = new StringBuffer();
        LinkedHashMap<String, String> paraMap = new LinkedHashMap<>();
        // 所有参与传参的参数按照accsii排序（升序）
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k))
            {
                sb.append(k + "=" + v + "&");
            }
        }
        if (needKey)
        {
            sb.append("key=" + EncryptOrDeCipher.aes_decrypt(privateKey));
        }
        String sign = MD5Util.MD5Encode(sb.toString()).toUpperCase();
        paraMap.put("parmStr", sb.toString());
        paraMap.put("sign", sign);
        return paraMap;
    }

}