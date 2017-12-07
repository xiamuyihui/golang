package com.kingthy.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 *
 * 登录注册
 * @author likejie
 * @date  2017/5/8.
 */
@Data
@ToString
public class UserInfoDTO implements Serializable
{
    
    private static final long serialVersionUID = 7899073777903688555L;
    
    /**
     * 用户头像
     */
    private String avatar;
    
    /**
     * 用户昵称
     */
    private String nikeName;
    
    /**
     * 用户签名
     */
    private String userSignature;
    
    /**
     * 性别
     */
    private Integer sex;
    
}
