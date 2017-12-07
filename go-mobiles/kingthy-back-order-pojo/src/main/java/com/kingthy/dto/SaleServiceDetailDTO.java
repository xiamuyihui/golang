package com.kingthy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kingthy.common.BaseReq;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 14:33 on 2017/6/6.
 * @Modified by:
 */
@Data
@ToString
public class SaleServiceDetailDTO implements Serializable {

/*  private boolean auditingFlag;//是否通过审核

    private String rejectReason;//审核不通过原因

    private int status;//0:发起售后 1:-审核通过 2:厂家确认收货,待生产 3:生产中 4:已包装发货，等待收货 5:已收货 6:已评价

    */

    /**
     * 进度列表
     * @Author:xumin
     * @Description:
     * @Date:10:34 2017/11/3
     */
    private List<? extends SaleCommonDto> listSchedule;

    private SaleServiceDetail detail;

    @Data
    @ToString
    public static class SaleServiceDetail{

        /**
         * 换货原因
         */
        private String exchangeReason;

        /**
         * 订单号
         */
        private String orderSn;

        /**
         * 发起售后时间
         */
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
        private Date createDate;

    }

    @Data
    @ToString
    public static class SaleCommonDto
    {
        /**
         * 0:发起售后 1:-审核通过 2:厂家确认收货,待生产 3:生产中 4:已包装发货，等待收货 5:已收货 6:已评价
         */
        private int status;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
        private Date createDate;
        private String memo;
    }

    /**
     * 提交申请
     */
    @Data
    @ToString
    public static class ApplyService extends SaleCommonDto{

    }

    //审核信息
    @Data
    @ToString
    public static class AuditingInfo extends SaleCommonDto{
        /**
         * 审核是否通过
         */
        private boolean flag;
        /**
         * 电话
         */
        private String phone;
        /**
         * 收货地址
         */
        private String address;
        /**
         * 收货人
         */
        private String consignee;
        /**
         * 退货地址
         */
        private String backAddress;
        /**
         * 审核不通过原因
         */
        private String rejectReason;

    }

    /**
     * 厂家确认收货，待生产
     */
    @Data
    @ToString
    public static class ConfirmInfo extends SaleCommonDto{

    }

    /**
     * 已包装发货，等待收货
     */
    @Data
    @ToString
    public static class GoodsReceipt extends SaleCommonDto{
        /**
         * 运单号
         */
        private String shippingNumber;
    }

    //生产进度
    @Data
    @ToString
    public static class ProducingInfo extends SaleCommonDto{

    }

    //物流进度
    @Data
    @ToString
    public static class ShippingInfo extends SaleCommonDto{

    }

    //已收货
    @Data
    @ToString
    public static class Completed extends SaleCommonDto{

    }

}
