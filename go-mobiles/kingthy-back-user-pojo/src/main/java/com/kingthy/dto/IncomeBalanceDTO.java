package com.kingthy.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 *
 * 粉丝
 * @author xumin
 * @date  2017/6/12 15:22
 */
@Data
@ToString
public class IncomeBalanceDTO implements Serializable {

    @ApiModelProperty("银行名称")
    private String bankName;
    @ApiModelProperty("卡号(尾号)")
    private String cardNo;
    @ApiModelProperty("单日限额")
    private Long limit;
    @ApiModelProperty("单笔限额")
    private Long singleLimit;
    @ApiModelProperty("绑定的银行卡uuid")
    private String bankCardUuid;

}
