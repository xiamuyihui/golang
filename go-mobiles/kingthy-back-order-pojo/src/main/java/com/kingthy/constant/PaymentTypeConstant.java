package com.kingthy.constant;

/**
 *
 * '支付方式类型 0:支付宝 1:微信 2:银联',
 * @author Created by likejie on 2017/6/23.
 */
public enum PaymentTypeConstant {

    /**支付宝*/   /**微信*/    /**银联*/
    ALIPAY(0),   WINXIN(1),   UNIONPAY(2);

    private  Integer value;

    public Integer getValue()
    {
        return value;
    }
    PaymentTypeConstant(Integer value)
    {
        this.value = value;
    }
}
