package com.kingthy.service.impl;

import java.io.IOException;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.constant.RabbitmqConstants;
import com.kingthy.dto.SmsReponseCode;
import com.kingthy.dubbo.message.service.MessagePushRecordDubboService;
import com.kingthy.entity.MessagePushRecord;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingthy.dto.MessageDto;
import com.kingthy.service.MessageService;

/**
 * MessageServiceImpl
 * <p>
 * @author yuanml 2017/8/21
 *
 * @version 1.0.0
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService
{
    
    private static final Logger LOG = LoggerFactory.getLogger(MessageServiceImpl.class);
    
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Reference(version = "1.0.0", timeout = 3000)
    private MessagePushRecordDubboService messagePushRecordDubboService;

    @Override
    public String send(MessageDto messageDto)
    {
        rabbitTemplate.convertAndSend(RabbitmqConstants.SmsMessageEnum.EXCHANGE.getValue(), RabbitmqConstants.SmsMessageEnum.ROUTING.getValue(), messageDto);
        return null;
    }

    @RabbitListener(queues = RabbitmqConstants.SMS_MESSAGE_LISTENER_QUEUE)
    public void sendSms(MessageDto messageDto)
        throws IOException, DocumentException
    {
        String mobile = messageDto.getPhone();
        String validateCode = messageDto.getValidateCode();
        final String url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        
        client.getParams().setContentCharset("GBK");
        method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=GBK");

        String content = new String("您的验证码是：" + validateCode + "。请不要把验证码泄露给其他人。");
        
        NameValuePair[] data = {// 提交短信
            // 查看用户名请登录用户中心->验证码、通知短信->帐户及签名设置->APIID
            new NameValuePair("account", "C93333765"),
            // 查看密码请登录用户中心->验证码、通知短信->帐户及签名设置->APIKEY
            new NameValuePair("password", "63cad66edfbd6fab2b7bc4dda7828523"),
            // new NameValuePair("password", util.StringUtil.MD5Encode("密码")),
            new NameValuePair("mobile", mobile), new NameValuePair("content", content),};
        method.setRequestBody(data);
        
        client.executeMethod(method);
        
        String submitResult = method.getResponseBodyAsString();
        

        Document doc = DocumentHelper.parseText(submitResult);
        Element root = doc.getRootElement();
        
        String code = root.elementText("code");
        String msg = root.elementText("msg");
        String smsid = root.elementText("smsid");
        
        LOG.info("code:" + code + ",msg:" + msg + ",smsid:" + smsid);
        MessagePushRecord record = new MessagePushRecord();
        record.setPhone(mobile);
        record.setPlatformUuid(url);
        record.setContent(content);
        if (code.equals(SmsReponseCode.SUCCESS.getValue()))
        {
            LOG.info("短信提交成功");
            record.setStatus(true);
        }else{
            record.setStatus(false);
        }
        messagePushRecordDubboService.insert(record);
    }

    @RabbitListener(queues = RabbitmqConstants.SMS_MESSAGE_LISTENER_QUEUE + "_A")
    public void sendSmsA(MessageDto messageDto)
        throws IOException, DocumentException
    {
        String mobile = messageDto.getPhone();
        String validateCode = messageDto.getValidateCode();
        final String url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);

        client.getParams().setContentCharset("GBK");
        method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=GBK");

        // int mobile_code = (int)((Math.random() * 9 + 1) * 100000)

        String content = new String("您的验证码是：" + validateCode + "。请不要把验证码泄露给其他人。");

        NameValuePair[] data = {// 提交短信
            // 查看用户名请登录用户中心->验证码、通知短信->帐户及签名设置->APIID
            new NameValuePair("account", "C93333765"),
            // 查看密码请登录用户中心->验证码、通知短信->帐户及签名设置->APIKEY
            new NameValuePair("password", "63cad66edfbd6fab2b7bc4dda7828523"),
            // new NameValuePair("password", util.StringUtil.MD5Encode("密码")),
            new NameValuePair("mobile", mobile), new NameValuePair("content", content),};
        method.setRequestBody(data);

        client.executeMethod(method);

        String submitResult = method.getResponseBodyAsString();

        Document doc = DocumentHelper.parseText(submitResult);
        Element root = doc.getRootElement();

        String code = root.elementText("code");
        String msg = root.elementText("msg");
        String smsid = root.elementText("smsid");

        LOG.info("code:" + code + ",msg:" + msg + ",smsid:" + smsid);
        MessagePushRecord record = new MessagePushRecord();
        record.setPlatformUuid(url);
        record.setPhone(mobile);
        record.setContent(content);
        if (code.equals(SmsReponseCode.SUCCESS.getValue()))
        {
            LOG.info("短信提交成功");
            record.setStatus(true);
        }else{
            record.setStatus(false);
        }
        messagePushRecordDubboService.insert(record);
    }
}
