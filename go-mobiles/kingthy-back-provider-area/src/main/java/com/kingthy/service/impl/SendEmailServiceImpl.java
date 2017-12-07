package com.kingthy.service.impl;

import com.kingthy.entity.Mail;
import com.kingthy.service.SendEmailService;
import com.kingthy.util.MailUtil;
import org.springframework.stereotype.Service;

/**
 * SendEmailServiceImapl(描述其作用)
 * <p>
 * @author 赵生辉 2017年06月06日 18:14
 *
 * @version 1.0.0
 */
@Service(value = "sendEmailService")
public class SendEmailServiceImpl implements SendEmailService
{
    @Override
    public boolean sendEmail(Mail mail)
    {
        boolean result = MailUtil.send(mail.getSmtp(),mail.getFrom(),mail.getTo(),mail.getSubject(),mail.getContent(),mail.getUsername(),mail.getPassword());
        return result;
    }
}
