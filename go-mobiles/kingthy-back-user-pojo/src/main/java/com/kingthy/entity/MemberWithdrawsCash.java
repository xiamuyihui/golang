package com.kingthy.entity;

import com.kingthy.common.BaseTableFileds;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * MemberWithdrawsCash
 * @author all
 * @date  2017/6/8.
 */
@Data
@ToString
public class MemberWithdrawsCash extends BaseTableFileds implements Serializable {


    private String membersUuid;
    /**0:申请提现 1:提现中 2:提现成功*/
    private Integer status;

    private String bankCardUuid;

    private BigDecimal money;

    private String cardNo;
    /**银行或支付宝,微信 返回的流水号*/
    private String orderId;

    private static final long serialVersionUID = 1L;

    public enum StatusType
    {
        /**
         * 申请提现
         */
        S_0(0),
        /**
         * 提现中
         */
        C_1(1),
        /**
         * 提现成功
         */
        E_2(2);

        private int value;

        public int getValue()
        {
            return value;
        }

        public void setValue(int value)
        {
            this.value = value;
        }

        StatusType(int value)
        {
            this.value = value;
        }

    }
}