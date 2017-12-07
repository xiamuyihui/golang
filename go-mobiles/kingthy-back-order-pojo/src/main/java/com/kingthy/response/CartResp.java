package com.kingthy.response;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author:xumin
 * @Description:
 * @Date:17:45 2017/11/2
 */
@Data
@ToString
public class CartResp implements Serializable
{
    private static final long serialVersionUID = 1L;

    private int quantity;

    private String cartItemUuid;

    private String desinger;

    private String goodsUuid;

    private String goodsStatus;

    private String goodsName;

    private String goodsFeature;

    private BigDecimal standardPrice;

    private String goodsImage;
}
