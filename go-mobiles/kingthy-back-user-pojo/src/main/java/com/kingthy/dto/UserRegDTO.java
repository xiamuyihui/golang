package com.kingthy.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 *
 * 登录注册
 * @author likejie
 * @date  2017/5/8.
 */
@Data
@ToString
public class UserRegDTO implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", required = true)
    private String phone;
    
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", required = true)
    private String pwd;
    
    private String verificationCode;
    
    private String regIp;
}
