package com.kingthy.dto;

import com.kingthy.entity.OrderItem;
import com.kingthy.entity.OrderLog;
import com.kingthy.entity.Orders;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 
 *
 * OrderInfoDto()
 * 
 * @author 赵生辉 2017年4月13日 下午8:27:08
 * 
 * @version 1.0.0
 *
 */
@Data
@ToString
public class OrderInfoDto extends Orders
{
    /**
     * 订单明细集合
     */
    private List<OrderItem> orderItems;
    
    /**
     * 订单记录集合
     */
    private List<OrderLog> orderLogs;
}
