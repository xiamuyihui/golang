package com.kingthy.request;

import com.kingthy.common.BaseReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;


/**
 *
 * WithdrawReq
 * @author xumin
 * @date 14:50 on 2017/6/14.
 *
 */
@Data
@ToString
public class WithdrawReq extends BaseReq implements java.io.Serializable{

    @ApiModelProperty("绑定的银行卡uuid")
    private String bankCardUuid;

    @ApiModelProperty("提现金额")
    private Double money;

    private  String cardNo;

    private  String membersUuid;

}
