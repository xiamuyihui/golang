package com.kingthy.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kingthy.common.BaseReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


/**
 *
 * UpdateWithDrawStatusReq
 * @author xumin
 * @date 16:18 on 2017/6/22.
 *
 */
@Data
@ToString
public class UpdateWithDrawStatusReq extends BaseReq implements java.io.Serializable{

    @ApiModelProperty("提现记录uuid")
    private String withdrawsUuid;

    @ApiModelProperty("银行或支付宝,微信 返回的流水号")
    private String orderId;

    @ApiModelProperty("会员uuid")
    private String memberUuid;

    @ApiModelProperty("提现状态:0:申请提现 1:提现中 2:提示成功")
    private Integer status;

    @JsonIgnore
    private Integer version;
}
