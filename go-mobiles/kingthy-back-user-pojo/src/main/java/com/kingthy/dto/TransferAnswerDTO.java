package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 *
 * 银联-代付，应答DTO
 * @author likejie
 * @date  2017/6/22.
 */
@Data
@ToString
public class TransferAnswerDTO implements Serializable {

    /**订单ID***/
    private String orderId;
    /**银行流水号，查询id***/
    private String queryId;
    /**银行卡号***/
    private String accNo;
    /**交易时间***/
    private String txnTime;
    /**交易金额***/
    private String txnAmt;
}
