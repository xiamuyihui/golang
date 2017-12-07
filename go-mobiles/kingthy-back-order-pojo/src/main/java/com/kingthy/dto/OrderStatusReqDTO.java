package com.kingthy.dto;

import com.kingthy.common.BaseReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 11:42 on 2017/4/20.
 * @Modified by:
 */
@Data
@ToString
public class OrderStatusReqDTO extends BaseReq implements Serializable {

    private static final long serialVersionUID = 5159687984499008486L;

    @ApiModelProperty("订单状态 0:代付款 1:生产中 2:待发货 3:待签收 4:待评价 5:已取消 6:待退单 7:已完成")
    private Integer status;

    @ApiModelProperty("订单uuid")
    private String orderUuId;

    @ApiModelProperty("订单号")
    private String orderSn;

    @ApiModelProperty("详细原因")
    private String content;

    @ApiModelProperty("支付方式类型 0:支付宝 1:微信 2:银联 3:QQ钱包 4:余额")
    private Integer paymentMethodType;
}
