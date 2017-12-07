package com.kingthy.dto;

import com.kingthy.common.BaseReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
/**
 * @author:xumin
 * @Description:
 * @Date:18:04 2017/11/2
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class OrderConfirmPayDTO extends BaseReq implements Serializable
{
    private static final long serialVersionUID = 51591384499008486L;

    /**
     * 商品ID
     */
    @ApiModelProperty("商品ID")
    private String productId;

    /**
     * 购物车ID
     */
    @ApiModelProperty("购物车ID")
    private String cartId;

    /**
     * 生成订单类型: 0 商品生成订单 1 购物车生成订单
     */
    @ApiModelProperty("生成订单类型: 0 商品生成订单 1 购物车生成订单")
    private Long createType;

}
