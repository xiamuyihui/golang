package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * MessageDto
 * <p>
 * @author yuanml 2017/8/21
 *
 * @version 1.0.0
 */
@Data
@ToString
public class MessageDto implements Serializable
{
    /**
     * 手机
     */
    private String phone;

    /**
     * 验证码
     */
    private String validateCode;
}
