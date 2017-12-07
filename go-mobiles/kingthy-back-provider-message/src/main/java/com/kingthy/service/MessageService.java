package com.kingthy.service;

import com.kingthy.dto.MessageDto;

/**
 * MessageService
 * <p>
 * @author yuanml
 * 2017/8/21
 *
 * @version 1.0.0
 */
@FunctionalInterface
public interface MessageService
{
    /**
     * 发送信息
     * @param messageDto
     * @return
     */
    String send(MessageDto messageDto);
}
