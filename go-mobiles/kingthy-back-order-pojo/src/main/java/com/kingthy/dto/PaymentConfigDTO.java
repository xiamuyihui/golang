package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * 支付参数配置
 *
 * @author Created by likejie on 2017/6/23.
 */
@Data
@ToString
public class PaymentConfigDTO implements Serializable {

    private String uuid;
    /**平台名称****/
    private String name;
    /**平台类型，1：支付宝，2微信，3银联****/
    private Integer type;
    /**商户号****/
    private String mchId;
    /**应用id****/
    private String appId;
    /**安全私钥****/
    private String appKey;
    /**appid的密码，接口调用授权****/
    private String appSecret;
    /**是否启用****/
    private Boolean isUsed;
}
