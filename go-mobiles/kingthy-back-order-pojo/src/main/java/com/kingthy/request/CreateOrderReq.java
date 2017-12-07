package com.kingthy.request;

import com.kingthy.entity.OrderItem;
import com.kingthy.entity.OrderLog;
import com.kingthy.entity.Orders;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 10:24 on 2017/8/17.
 * @Modified by:
 */

@Data
@ToString
public class CreateOrderReq implements Serializable
{

    private static final long serialVersionUID = 1L;

    private Orders orders;

    private List<OrderItem> orderItemList;

}
