package com.kingthy.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 *
 * 修改支付密码DTO
 * @author likejie
 * @date  2017/6/15.
 */
@Data
@ToString
public class ModifyPayPasswordDTO implements Serializable {

    @JsonIgnore
    private String token;
    /**
     *
     * 用户uuid
     */
    private String uuid;
    /**
     *
     * 验证码
     */
    private String code;
    /**
     *
     * 手机号
     */
    private String phone;
    /**
     *
     * 登录密码
     */
    private String password;

    /**
     *
     * 支付密码
     */
    private String paymentPassword;
}
