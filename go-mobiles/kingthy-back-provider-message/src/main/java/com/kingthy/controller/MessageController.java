package com.kingthy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kingthy.dto.MessageDto;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.MessageService;

import io.swagger.annotations.ApiParam;

/**
 * MessageController
 * <p>
 * @author yuanml 2017/8/21
 *
 * @version 1.0.0
 */
@RestController()
@RequestMapping("/message")
public class MessageController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);
    
    @Autowired
    private MessageService messageService;
    
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public WebApiResponse<String> sendMessage(
        @RequestBody @ApiParam(name = "messageDto", value = "发送信息参数") MessageDto messageDto)
    {
        LOGGER.info("发送短信" + messageDto);
        try
        {
            String result = messageService.send(messageDto);
            return WebApiResponse.success(result);
        }
        catch (Exception e)
        {
            LOGGER.error("/message/send "+e);
            return WebApiResponse.error(e.getMessage());
        }
    }
}
