package com.kingthy.controller;

import com.kingthy.entity.Mail;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * SendMassageController(描述其作用)
 * <p>
 * @author 赵生辉 2017年06月06日 17:55
 *
 * @version 1.0.0
 */
@RestController
@RequestMapping("/email")
public class SendMessageController
{
    @Autowired
    private SendEmailService sendEmailService;

    @PostMapping("/send")
    public WebApiResponse<?> sendEmail(@RequestBody Mail mail,
        @RequestHeader("Authorization") String token){
        boolean result = sendEmailService.sendEmail(mail);
        return WebApiResponse.success(result);
    }


}
