package com.kingthy.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.service.PaymentConfigService;
import com.kingthy.unionpay.sdk.SdkConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.kingthy.constant.PaymentTypeConstant;
import com.kingthy.dto.PaymentConfigDTO;
import com.kingthy.dto.RealNameVerifiedDTO;
import com.kingthy.dto.TransferAnswerDTO;
import com.kingthy.dto.TransferDTO;
import com.kingthy.dubbo.payment.service.PaymentLogDubboService;
import com.kingthy.entity.PaymentLog;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.UnionPayementService;
import com.kingthy.unionpay.DemoBase;
import com.kingthy.unionpay.sdk.AcpService;

/**
 *
 * 银联支付--代付业务
 * @author likejie 2017/6/16.
 */
@Service
public class UnionPayementServiceImpl implements UnionPayementService
{
    
    private static final Logger logger = LoggerFactory.getLogger(UnionPayementService.class);
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    
    @Reference(version = "1.0.0",timeout = 3000)
    private PaymentLogDubboService paymentLogDubboService;

    @Autowired
    private PaymentConfigService paymentConfigService;
    /**
     *
     * 发送代付请求
     */
    @Override
    public WebApiResponse<?> sendTransferRequest(TransferDTO dto)
    {
        
        PaymentConfigDTO paymentConfig = paymentConfigService.getPaymentConfig(PaymentTypeConstant.UNIONPAY.getValue());
        
        logger.info("银联请求开始：dto：" + dto);
        
        /** 商户号 **/
        String merId = paymentConfig.getMchId();
        /** 交易金额 单位为分，不能带小数点 **/
        String txnAmt = Long.toString(dto.getAmount());
        /** 订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则 **/
        String orderId = dto.getOrderId();
        /** 订单发送时间，格式要求：yyyyMMddHHmmss */
        String txnTime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        if (dto.getTxnTime() == null)
        {
            Date now = new Date();
            txnTime = sdf.format(now);
        }
        else
        {
            txnTime = sdf.format(dto.getTxnTime());
        }
        
        /** 提现会员相关信息start ***/
        //身份证号：341126197709218366
        String certifId = dto.getCertifId();
        // 银行卡号：6216261000000000018;
        String accNo = dto.getAccNo();
        /** 提现会员相关信息end ***/
        
        Map<String, String> data = new HashMap<>(18);
        
        /*** 银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改 ***/
        // 版本号 全渠道默认值
        data.put("version", DemoBase.version);
        // 字符集编码 可以使用UTF-8,GBK两种方式
        data.put("encoding", DemoBase.encoding);
        // 签名方法
        data.put("signMethod", SdkConfig.getConfig().getSignMethod());
        // 交易类型 12：代付
        data.put("txnType", "12");
        // 默认填写00
        String defaultValue = "00";
        data.put("txnSubType", defaultValue);
        // 000401：代付
        data.put("bizType", "000401");
        // 渠道类型
        data.put("channelType", "07");
        
        /*** 商户接入参数 ***/

        // 商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
        data.put("merId", merId);
        // 接入类型，商户接入填0 ，不需修改（0：直连商户， 1： 收单机构 2：平台商户）
        data.put("accessType", "0");
        // 商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
        data.put("orderId", orderId);
        // 订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
        data.put("txnTime", txnTime);
        // 账号类型 01：银行卡02：存折03：IC卡帐号类型(卡介质)
        data.put("accType", "01");

        /***如果商户号开通了 商户对敏感信息加密的权限那么，需要对 卡号accNo加密使用**/
        // 上送敏感信息加密域的加密证书序列号
        data.put("encryptCertId", AcpService.getEncryptCertId());
        // 这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
        String encrypAccNo = AcpService.encryptData(accNo, DemoBase.encoding);
        data.put("accNo", encrypAccNo);

        
        /**商户未开通敏感信息加密的权限那么不对敏感信息加密使用：**/
        //这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
        // contentData.put("accNo", "6216261000000000018");

        /**代收交易的上送的卡验证要素为：姓名或者证件类型+证件号码 **/
        Map<String, String> customerInfoMap = new HashMap<>(2);
        // 证件类型 01 身份证
        customerInfoMap.put("certifTp", "01");
        // 证件号码
        customerInfoMap.put("certifId", certifId);
        String customerInfoStr = AcpService.getCustomerInfo(customerInfoMap, accNo, DemoBase.encoding);
        
        data.put("customerInfo", customerInfoStr);
        // 交易金额 单位为分，不能带小数点
        data.put("txnAmt", txnAmt);
        // 境内商户固定 156 人民币
        data.put("currencyCode", "156");
        // data.put("billNo", "保险"); //银行附言。会透传给发卡行，完成改造的发卡行会把这个信息在账单、短信中显示给用户的，请按真实情况填写。
        
        /***后台通知地址（需设置为外网能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，【支付失败的交易银联不会发送后台通知】
        后台通知参数详见open.unionpay.com帮助中心 下载 产品接口规范 网关支付产品接口规范 消费交易 商户通知
        注意:
         1.需设置为外网能访问，否则收不到通知
         2.http https均可
         3.收单后台通知后需要10秒内返回http200或302状态码
         4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200或302，那么银联会间隔一段时间再次发送。总共发送5次，银联后续间隔1、2、4、5 分钟后会再次通知。
         5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败***/
        data.put("backUrl", DemoBase.backUrl);
        
        // 请求方保留域，
        // 透传字段，查询、通知、对账文件中均会原样出现，如有需要请启用并修改自己希望透传的数据。
        // 出现部分特殊字符时可能影响解析，请按下面建议的方式填写：
        // 1. 如果能确定内容不会出现&={}[]"'等符号时，可以直接填写数据，建议的方法如下。
        // data.put("reqReserved", "透传信息1|透传信息2|透传信息3");
        // 2. 内容可能出现&={}[]"'符号时：
        // 1) 如果需要对账文件里能显示，可将字符替换成全角＆＝｛｝【】“‘字符（自己写代码，此处不演示）；
        // 2) 如果对账文件没有显示要求，可做一下base64（如下）。
        // 注意控制数据长度，实际传输的数据长度不能超过1024位。
        // 查询、通知等接口解析时使用new String(Base64.decodeBase64(reqReserved), DemoBase.encoding);解base64后再对数据做后续解析。
        // data.put("reqReserved", Base64.encodeBase64String("任意格式的信息都可以".toString().getBytes(DemoBase.encoding)));
        
        /**对请求参数进行签名并发送http post请求，接收同步应答报文
        报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可**/
        Map<String, String> reqData = AcpService.sign(data, DemoBase.encoding);
        //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的acpsdk.backTransUrl
        String requestBackUrl = SdkConfig.getConfig().getBackRequestUrl();
        // 发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
        Map<String, String> rspData = AcpService.post(reqData, requestBackUrl, DemoBase.encoding);
        /** 对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考-------------> **/
        // 应答码规范参考open.unionpay.com帮助中心 下载 产品接口规范 《平台接入接口规范-第5部分-附录》
        if (!rspData.isEmpty())
        {
            if (AcpService.validate(rspData, DemoBase.encoding))
            {
                logger.info("验证签名成功");
                String respCode = rspData.get("respCode");
                String repsoneMessage = "应答码：" + respCode + "--(描述：" + getReponseInfo(respCode) + ")";
                if (defaultValue.equals(respCode))
                {
                    // 交易已受理(不代表交易已成功），等待接收后台通知确定交易成功，也可以主动发起 查询交易确定交易状态。
                    // TODO
                    TransferAnswerDTO answer = new TransferAnswerDTO();
                    answer.setAccNo(AcpService.decryptData(rspData.get("accNo"), DemoBase.encoding));
                    answer.setOrderId(rspData.get("orderId"));
                    answer.setQueryId(rspData.get("queryId"));
                    answer.setTxnAmt(rspData.get("txnAmt"));
                    answer.setTxnTime(rspData.get("txnTime"));
                    /**** 发送消息到队列进行处理 ****/
                    // rabbitTemplate.convertAndSend(UnionpayMqNameConstans.EXCHANGE_NAME,UnionpayMqNameConstans.ROUTING_KEY_NAME,orderId);
                    // 如果返回卡号且配置了敏感信息加密，解密卡号方法：
                    // String accNo1 = resmap.get("accNo");
                    // String accNo2 = AcpService.decryptPan(accNo1, "UTF-8"); //解密卡号使用的证书是商户签名私钥证书acpsdk.signCert.path
                    // LogUtil.writeLog("解密后的卡号："+accNo2);
                    logger.info("请求成功，交易已受理！");
                    PaymentLog paymentLog = new PaymentLog();
                    paymentLog.setSn(orderId);
                    paymentLog.setAmount(BigDecimal.valueOf(Long.parseLong(txnAmt)));
                    paymentLog.setPaymentPluginId("UnionPay");
                    paymentLog.setPaymentPluginName("银联支付");
                    paymentLog.setCreateDate(new Date());
                    paymentLogDubboService.insert(paymentLog);
                    return WebApiResponse.success(answer);
                }
                else
                {
                    // TODO 交易失败！
                    logger.error("交易失败！" + repsoneMessage);
                    return WebApiResponse.error("交易失败！" + repsoneMessage);
                }
            }
            else
            {
                // TODO 检查验证签名失败的原因
                logger.error("验证签名失败！");
                return WebApiResponse.error("验证签名失败！");
            }
        }
        else
        {
            // TODO 未返回正确的http状态
            logger.error("未获取到返回报文或返回http状态码非200");
            return WebApiResponse.error("未获取到返回报文或返回http状态码非200");
        }
    }
    
    @Override
    public WebApiResponse<?> queryTranStatus(String orderId)
    {
        
        PaymentConfigDTO paymentConfig =  paymentConfigService.getPaymentConfig(PaymentTypeConstant.UNIONPAY.getValue());
        // 商户号
        String merId = paymentConfig.getMchId();
        
        // 订单发送时间
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String txnTime = sdf.format(now);
        
        Map<String, String> data = new HashMap<>(10);
        
        /*** 银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改 ***/
        // 版本号
        data.put("version", DemoBase.version);
        // 字符集编码 可以使用UTF-8,GBK两种方式
        data.put("encoding", DemoBase.encoding);
        // 签名方法
        data.put("signMethod", SdkConfig.getConfig().getSignMethod());
        String defaultValue = "00";
        // 交易类型 00-默认
        data.put("txnType", defaultValue);
        // 交易子类型 默认00
        data.put("txnSubType", defaultValue);
        // 业务类型 代付
        data.put("bizType", "000401");

        /*** 商户接入参数 ***/
        // 商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
        data.put("merId", merId);
        // 接入类型，商户接入固定填0，不需修改
        data.put("accessType", "0");

        /*** 要调通交易以下字段必须修改 ***/
        // ****商户订单号，每次发交易测试需修改为被查询的交易的订单号
        data.put("orderId", orderId);
        // ****订单发送时间，每次发交易测试需修改为被查询的交易的订单发送时间
        data.put("txnTime", txnTime);

        /** 请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文-------------> **/
        
        // 报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
        Map<String, String> reqData = AcpService.sign(data, DemoBase.encoding);
        // 交易请求url从配置文件读取对应属性文件acp_sdk.properties中的
        String url = SdkConfig.getConfig().getSingleQueryUrl();
        // 发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
        Map<String, String> rspData = AcpService.post(reqData, url, DemoBase.encoding);

        /** 对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考-------------> **/
        
        // 应答码规范参考open.unionpay.com帮助中心 下载 产品接口规范 《平台接入接口规范-第5部分-附录》
        if (!rspData.isEmpty())
        {
            if (AcpService.validate(rspData, DemoBase.encoding))
            {
                logger.info("验证签名成功");
//                返回码
                String respCode = "respCode";
                if (defaultValue.equals(rspData.get(respCode)))
                {// 如果查询交易成功
                    String origRespCode = rspData.get("origRespCode");
                    // 处理被查询交易的应答码逻辑
                    String repsoneMessage = "应答码：" + origRespCode + "--(描述：" + getReponseInfo(origRespCode) + ")";
//                    原始返回值
                    String origResp = "A6";
                    if (defaultValue.equals(origRespCode) || (origResp).equals(origRespCode))
                    {
                        // A6代付交易返回，参与清算，商户应该算成功交易，根据成功的逻辑处理
                        // 交易成功，更新商户订单状态
                        // TODO
                        logger.info("订单号：" + orderId + "交易成功");
                        return WebApiResponse.success(orderId);
                    }
                    else
                    {
                        // 其他应答码为交易失败
                        // TODO
                        logger.error("订单号：" + orderId + "交易失败！" + repsoneMessage);
                        return WebApiResponse.error("订单号：" + orderId + "交易失败！" + repsoneMessage);
                    }
                }
                else
                {
//订单不存在返回值
                    String s = "34";
                    if (s.equals(rspData.get(respCode)))
                    {
                        // 订单不存在，可认为交易状态未明，需要稍后发起交易状态查询，或依据对账结果为准
                        logger.error("订单不存在");
                        return WebApiResponse.error("订单不存在！");
                    }
                    else
                    {// 查询交易本身失败，如应答码10/11检查查询报文是否正确
                     // TODO
                        logger.error("查询交易失败");
                        return WebApiResponse.error("查询交易失败！");
                    }
                }
            }
            else
            {
                logger.error("验证签名失败");
                // TODO 检查验证签名失败的原因
                return WebApiResponse.error("验证签名失败！");
            }
        }
        else
        {
            // 未返回正确的http状态
            logger.error("未获取到返回报文或返回http状态码非200");
            return WebApiResponse.error("未获取到返回报文或返回http状态码非200！");
        }
        
    }
    
    /**
     *
     * 银联代付回调处理
     *
     **/
    @Override
    public void unionTransferCallBack()
    {
        
    }

    
    /**
     *
     * 实名认证
     *
     **/
    @Override
    public WebApiResponse<?> realNameVerified(RealNameVerifiedDTO dto)
    {
        
        PaymentConfigDTO paymentConfig = paymentConfigService.getPaymentConfig(PaymentTypeConstant.UNIONPAY.getValue());
        
        /** 商户号 **/
        String merId = paymentConfig.getMchId();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date now = new Date();
        
        // 订单时间
        String txnTime = sdf.format(now);
        // 订单ID
        String orderId = Long.toString(System.currentTimeMillis());
        
        /** 用户相关信息start ***/
        // 身份证号：341126197709218366;
        String certifId = dto.getCertifId();
        // 银行卡号：6216261000000000018;
        String accNo = dto.getAccNo();
        // 手机号;
        String phone = dto.getPhone();
        // 客户名称;
        String customerNm = dto.getCustomerNm();
        /** 提现会员相关信息end ***/
        
        Map<String, String> data = new HashMap<>(16);
        
        /*** 银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改 ***/
        // 版本号
        data.put("version", DemoBase.version);
        // 字符集编码 可以使用UTF-8,GBK两种方式
        data.put("encoding", DemoBase.encoding);
        // 签名方法
        data.put("signMethod", SdkConfig.getConfig().getSignMethod());
        // 交易类型 11-代收
        data.put("txnType", "72");
        // 交易子类型 01-实名认证
        data.put("txnSubType", "01");
        // 业务类型 代收产品
        data.put("bizType", "000401");
        // 渠道类型07-PC
        data.put("channelType", "07");

        /*** 商户接入参数 ***/
        // 商户号码（商户号码777290058110097仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
        data.put("merId", merId);
        // 接入类型，商户接入固定填0，不需修改
        data.put("accessType", "0");

        /** 用户实名认证时，此参数也要传 ****/
        // 商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
        data.put("orderId", orderId);
        /** 用户实名认证时，此参数也要传 ****/
        // 订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
        data.put("txnTime", txnTime);
        // 账号类型
        data.put("accType", "01");

        // 实名认证交易的customerInfo送什么验证要素是在银联后台到商户号上的，一般在入网的时候银联业务会与商户业务商谈好这些内容，请咨询您的业务人员或者银联业务运营的同事
        // 以下上送要素是参考《测试商户号777290058110097实名认证交易必送验证要素配置说明.txt》借记卡（实名认证交易-后台）部分
        Map<String, String> customerInfoMap = new HashMap<>(4);
        // 证件类型
        customerInfoMap.put("certifTp", "01");
        // 证件号码
        customerInfoMap.put("certifId", certifId);
        // 姓名
        customerInfoMap.put("customerNm", customerNm);
        // 手机号
        customerInfoMap.put("phoneNo", phone);
        // 卡背面的cvn2三位数字
        // customerInfoMap.put("cvn2", "123");
        // 有效期 年在前月在后
        // customerInfoMap.put("expired", "1711");

        // 如果商户号开通了【商户对敏感信息加密】的权限那么需要对 accNo，pin和phoneNo，cvn2，expired加密（如果这些上送的话），对敏感信息加密使用：
        // 这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
        String encryptAccNo = AcpService.encryptData(accNo, "UTF-8");
        data.put("accNo", encryptAccNo);
        // 加密证书的certId，配置在acp_sdk.properties文件
        data.put("encryptCertId", AcpService.getEncryptCertId());
        // acpsdk.encryptCert.path属性下
        String customerInfoStr = AcpService.getCustomerInfoWithEncrypt(customerInfoMap, accNo, DemoBase.encoding);

        //// 如果商户号未开通【商户对敏感信息加密】权限那么不需对敏感信息加密使用：
        // contentData.put("accNo", "6216261000000000018"); //这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
        // String customerInfoStr = AcpService.getCustomerInfo(customerInfoMap,"6216261000000000018");

        data.put("customerInfo", customerInfoStr);
        
        /** 对请求参数进行签名并发送http post请求，接收同步应答报文 **/
        // 报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
        Map<String, String> reqData = AcpService.sign(data, DemoBase.encoding);
        // 交易请求url从配置文件读取对应属性文件acp_sdk.properties中的
        String requestBackUrl = SdkConfig.getConfig().getBackRequestUrl();
        // acpsdk.backTransUrl
        // 发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
        Map<String, String> rspData = AcpService.post(reqData, requestBackUrl, DemoBase.encoding);

        /** 对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考-------------> **/
        // 应答码规范参考open.unionpay.com帮助中心 下载 产品接口规范 《平台接入接口规范-第5部分-附录》
        if (!rspData.isEmpty())
        {
            if (AcpService.validate(rspData, DemoBase.encoding))
            {
                logger.info("验证签名成功");
                String respCode = rspData.get("respCode");
                String repsoneMessage = "应答码：" + respCode + "--(描述：" + getReponseInfo(respCode) + ")";
//                认证成功返回值
                String successCode = "00";
                if (successCode.equals(respCode))
                {
                    // 交易成功
                    // TODO
                    logger.info("认证成功");
                    return WebApiResponse.success("认证成功！");
                }
                else
                {
                    logger.error("认证失败！" + repsoneMessage);
                    return WebApiResponse.error("认证失败！" + repsoneMessage);
                }
            }
            else
            {
                logger.error("验证签名失败");
                return WebApiResponse.error("验证签名失败");
            }
        }
        else
        {
            // 未返回正确的http状态
            logger.error("未获取到返回报文或返回http状态码非200");
            return WebApiResponse.error("未获取到返回报文或返回http状态码非200");
        }
    }
    
    public String getReponseInfo(String reponseCode)
    {
        
        switch (reponseCode)
        {
            
            case "00":
                return "成功！";
            case "A6":
                return "有缺陷的成功！";
            case "01":
                return "交易失败。详情请咨询95516！";
            case "02":
                return "系统未开放或暂时关闭，请稍后再试！";
            case "03":
                return "交易通讯超时，请发起查询交易！";
            case "04":
                return "交易状态未明，请查询对账结果！";
            case "05":
                return "交易已受理，请稍后查询交易结果！";
            case "06":
                return "系统繁忙，请稍后再试！";
            
            case "10":
                return "报文格式错误！";
            case "11":
                return "验证签名失败！";
            case "12":
                return "重复交易！";
            case "13":
                return "报文交易要素缺失！";
            case "14":
                return "批量文件格式错误！";
            
            case "30":
                return "交易未通过，请尝试使用其他银联卡支付或联系95516！";
            case "31":
                return "商户状态不正确！";
            case "32":
                return "无此交易权限！";
            case "33":
                return "交易金额超限！";
            case "34":
                return "查无此交易！";
            
            case "35":
                return "原交易不存在或状态不正确！";
            case "36":
                return "与原交易信息不符！";
            case "37":
                return "已超过最大查询次数或操作过于频繁！";
            case "38":
                return "银联风险受限！";
            case "39":
                return "交易不在受理时间范围内！";
            case "40":
                return "绑定关系检查失败！";
            case "41":
                return "批量状态不正确，无法下载！";
            case "42":
                return "扣款成功但交易超过规定支付时间！";
            case "43":
                return "无此业务权限，详情请咨询95516！";
            case "44":
                return "输入卡号错误或暂未开通此项业务，详情请咨询95516！";
            case "60":
                return "交易失败，详情请咨询您的发卡行！";
            case "61":
                return "输入的卡号无效，请确认后输入！";
            case "62":
                return "交易失败，发卡银行不支持该商户，请更换其他银行卡！";
            
            case "63":
                return "卡状态不正确！";
            case "64":
                return "卡上的余额不足！";
            case "65":
                return "输入的密码、有效期或CVN2有误，交易失败\n！";
            case "66":
                return "持卡人身份信息或手机号输入不正确，验证失败！";
            case "67":
                return "密码输入次数超限";
            case "68":
                return "您的银行卡暂不支持该业务，请向您的银行或95516咨询";
            case "69":
                return "您的输入超时，交易失败！";
            case "70":
                return "交易已跳转，等待持卡人输入！";
            case "71":
                return "动态口令或短信验证码校验失败！";
            case "72":
                return "您尚未在{}银行网点柜面或个人网银签约加办银联无卡支付业务，请去柜面或网银开通或拨打{}！";
            case "73":
                return "支付卡已超过有效期！";
            case "74":
                return "扣款成功，销账未知！";
            case "75":
                return "扣款成功，销账失败！";
            case "76":
                return "需要验密开通！";
            case "77":
                return "银行卡未开通认证支付";
            case "78":
                return "发卡行交易权限受限，详情请咨询您的发卡行！";
            case "79":
                return "此卡可用，但发卡行暂不支持短信验证！";
            case "80":
                return "交易失败，Token 已过期！";
            
            case "82":
                return "需要校验密码！";
            case "98":
                return "文件不存在！";
            case "99":
                return "通用错误！";
            
            default:
                return "无法解析错误！";
        }
        
    }
    
}
