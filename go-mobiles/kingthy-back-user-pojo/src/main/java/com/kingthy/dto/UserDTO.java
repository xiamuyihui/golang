package com.kingthy.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 *
 * 登录注册
 * @author likejie
 * @date  2017/5/8.
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "登录注册", description = "用户模型")
public class UserDTO implements Serializable
{
    
    private static final long serialVersionUID = -9078734818848659684L;
    
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
    
    @ApiModelProperty(value = "客户端ID", required = true)
    private String clientId;
}
