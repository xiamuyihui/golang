package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 15:51 on 2017/8/18.
 * @Modified by:
 */
@Data
@ToString
public class EventPublishReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private String memberUuid;

    private List<GoodsAndQuantity> goodsAndQuantityList;

    @Data
    @ToString
    public static class GoodsAndQuantity implements Serializable
    {

        private String goodsUuid;
        /**
         * 数量
         */
        private Integer quantity;
    }
}
