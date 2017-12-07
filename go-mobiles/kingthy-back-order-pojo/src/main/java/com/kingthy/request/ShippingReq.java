package com.kingthy.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import java.util.Date;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 13:59 on 2017/7/12.
 * @Modified by:
 */
@Data
@ToString
public class ShippingReq implements java.io.Serializable{


    /**
     * 当前页数
     */
    private Integer pageNum;

    /**
     * 每页展示的数量
     */
    private Integer pageSize;

    @ApiModelProperty("订单号")
    private String sn;

    @ApiModelProperty("会员")
    private String memberName;

    @ApiModelProperty("设计师")
    private String desingerName;

    @ApiModelProperty("订单状态 0:代付款 1:生产中 2:待发货 3:待签收 4:待评价 5:已取消 6:待退单 7:已完成")
    private Integer status;

    @ApiModelProperty("下单时间")
    private Long beginTime;

    @ApiModelProperty("下单时间")
    private Long endTime;

    private Long priceMin;

    private Long priceMax;

    @Data
    @ToString
    public class DeliveryReq{
        @ApiModelProperty("售后状态 0:发起售后 1:-审核通过 2:厂家确认收货,待生产 3:生产中 4:已包装发货，等待收货 5:已收货 6:已评价")
        private Integer status;

        @ApiModelProperty("用户")
        private String memberUuid;

        @ApiModelProperty("订单号")
        private String orderSn;

        @ApiModelProperty("运单号")
        private String shippingNumber;

        private String uuid;
    }

    @Data
    @ToString
    public class EditAddress{

        /**
         * orders 表的 uuid
         */
        @JsonIgnore
        private String ordersUuid;

        /**
         * order_item 表的 uuid
         */
        private String uuid;

        @ApiModelProperty("订单号")
        private String orderSn;

        @ApiModelProperty("用户")
        private String memberUuid;

        @ApiModelProperty("地址")
        private String address;
    }

    @Data
    @ToString
    public class OrderShipping{

        @ApiModelProperty("订单号")
        private String orderSn;
        @ApiModelProperty("下单时间")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
        private Date createDate;
        private String goodsName;
        private Integer quantity;
        private String figureName;
        private Double amount;
        private String shippingMethodName;
        private String memo;
        private String invoiceTitle;
        private String address;
        private String areaName;
        private String consignee;
        private String phone;
        private String zipCode;
    }
}
