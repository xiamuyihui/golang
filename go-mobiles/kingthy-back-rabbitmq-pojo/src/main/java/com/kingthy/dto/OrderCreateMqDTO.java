package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 14:05 on 2017/11/6.
 * @Modified by:
 */
@Data
@ToString
public class OrderCreateMqDTO extends BaseRabbitMqDTO implements Serializable {

    /**
     * 主订单号
     */
    private String orderSn;
    /**
     * 订单价格
     */
    private Double price;

    /**
     * 会员ID
     */
    private String memberUuid;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 订单原始请求参数
     */
    private OrderDTO orderDTO;

    /**
     * 商品信息
     */
    private List<GoodsOrderDTO> goodsOrderDTOList;

}
