package com.kingthy.request;

import com.kingthy.common.BaseReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 *
 * BindingBankReq
 * @author xumin
 * @date 2017/6/14.
 *
 */
@Data
@ToString
public class BindingBankReq extends BaseReq implements java.io.Serializable{

    @ApiModelProperty("卡号")
    private String cardNo;

    @ApiModelProperty("银行名称")
    private String bankName;

    @ApiModelProperty("银行代码")
    private String bankCode;

    @ApiModelProperty("持卡人")
    private String cardholder;

    @ApiModelProperty("身份证")
    private String identityCard;

    @ApiModelProperty("手机号")
    private String phone;
}
