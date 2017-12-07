package com.kingthy.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


import java.io.Serializable;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 10:52 on 2017/7/11.
 * @Modified by:
 */
@Data
@ToString
public class AfterSaleServiceReq implements Serializable {


    private Integer pageNum;


    private Integer pageSize;

    /**
     * 售后状态
     */
    @ApiModelProperty("售后状态 0:发起售后 1:-审核通过 2:厂家确认收货,待生产 3:生产中 4:已包装发货，等待收货 5:已收货 6:已评价")
    private Integer status;

    @ApiModelProperty("订单号")
    private String orderSn;
    @ApiModelProperty("商品名称")
    private String goodsName;
    private Long beginDate;
    private Long endDate;
    @ApiModelProperty("用户")
    private String memberName;
    @ApiModelProperty("换货说明")
    private String memo;
    private String uuid;

    @Data
    @ToString
    public static class EditStatusReq implements Serializable{

        @ApiModelProperty("售后状态 0:发起售后 1:-审核通过 2:厂家确认收货,待生产 3:生产中 4:已包装发货，等待收货 5:已收货 6:已评价")
        private Integer status;

        @ApiModelProperty("用户")
        private String memberUuid;

        private String uuid;

        @ApiModelProperty("运单号")
        private String shippingNumber;

        @ApiModelProperty("是否删除")
        private Boolean delFlag;

        @ApiModelProperty("售后地址")
        private String refundsAddrUuid;

    }

    @Data
    @ToString
    public static class RejectReq implements Serializable{

        @ApiModelProperty("用户")
        private String memberUuid;

        @ApiModelProperty("审核不通过原因")
        private String rejectReason;

        private String uuid;
    }
}
