package com.kingthy.service;

import com.kingthy.entity.Mail;

/**
 * SendEmailService(描述其作用)
 * <p>
 * @author 赵生辉 2017年06月06日 18:10
 *
 * @version 1.0.0
 */
public interface SendEmailService
{
    /**
     * 发送邮件接口
     * @param mail
     * @return
     */
    boolean sendEmail(Mail mail);
}
