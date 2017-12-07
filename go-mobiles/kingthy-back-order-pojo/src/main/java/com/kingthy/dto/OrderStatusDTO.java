package com.kingthy.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author Created by xumin on 2017/4/24.
 */
@Data
@ToString
public class OrderStatusDTO implements java.io.Serializable{

    @ApiModelProperty("收货地址")
    private String address;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("收货人")
    private String consignee;

    @ApiModelProperty("订单状态 0:代付款 1:生产中 2:待发货 3:待签收 4:待评价 5:已取消 6:待退单 7:已完成")
    private String status;

    @ApiModelProperty("订单号")
    private String sn;
}
