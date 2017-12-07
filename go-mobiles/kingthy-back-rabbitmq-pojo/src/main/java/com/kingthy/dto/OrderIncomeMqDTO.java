package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 13:54 on 2017/6/15.
 * @Modified by:
 */
@Data
@ToString
public class OrderIncomeMqDTO extends BaseRabbitMqDTO implements Serializable {
    /**
     * 订单号
     */
    private String orderSn;
    /**
     * 会员uuid
     */
    private String memberUuid;
}
