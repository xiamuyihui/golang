package com.kingthy.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author  likejie on 2017/6/16.
 */
@Data
@ToString
public class TransferDTO implements Serializable {



    @ApiModelProperty("会员uuid")
    private String memberUuid;

    @ApiModelProperty("交易金额 单位为分，不能带小数点")
    private  Long amount;

    @ApiModelProperty("商户订单号，8-40位数字字母，不能含“-”或“_”")
    private  String orderId;

    @ApiModelProperty("证件号码")
    private String certifId;

    @ApiModelProperty("客户名称")
    private String customerNm;

    @ApiModelProperty("银行卡号")
    private String accNo;

    @ApiModelProperty("订单发送时间")
    private Date txnTime;


}
