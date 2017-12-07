package com.kingthy.entity;

import com.kingthy.common.BaseTableFileds;
import lombok.Data;
import lombok.ToString;

/**
 * 订单售后进度表
 * @author xumin
 * @Description:
 * @DATE Created by 15:53 on 2017/6/6.
 * @Modified by:
 */
@Data
@ToString
public class AfterSaleSchedule extends BaseTableFileds {

    private String goodsUuid;

    private String memo;

    private String saleServiceUuid;

    /**
     * 0:发起售后 1:-审核通过 2:厂家确认收货,待生产 3:生产中 4:已包装发货，等待收货 5:已收货 6:已评价
     */
    private Integer status;

    /**
     * 订单号
     */
    private String orderSn;
}
