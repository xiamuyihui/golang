package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 11:29 on 2017/6/20.
 * @Modified by:
 */
@Data
@ToString
public class WithdrawsCashMqDTO extends BaseRabbitMqDTO implements Serializable {

    /**
     * 提现记录uuid
     */
    private String withdrawsUuid;
    /**
     * 会员uuid
     */
    private String memberUuid;
    /**
     * 银行卡号
     */
    private String accNo;
    /**
     * 证件号码
     */
    private String certifId;
    /**
     * 商户订单号，8-40位数字字母，不能含“-”或“_”" (提现记录的uuid)
     */
    private String orderId;
    /**
     * 交易金额 单位为分，不能带小数点
     */
    private Long amount;
}
