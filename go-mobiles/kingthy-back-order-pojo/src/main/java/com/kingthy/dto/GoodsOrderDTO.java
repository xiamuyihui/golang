package com.kingthy.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Created by KingThy on 2017/4/18.
 */

@Data
@ToString
public class GoodsOrderDTO implements Serializable{

    private static final long serialVersionUID = 726080152327286989L;

    /**
     * 数量
     */
    @ApiModelProperty("数量")
    private Integer quantity;
    /**
     * 购物车ID
     */
    @ApiModelProperty("购物车ID")
    private String cartUuid;
    /**
     * 商品ID
     */
    @ApiModelProperty("商品ID")
    private String goodsUuid;

    /**
     * 商品上架状态：0：未上架，1：已上架，2：延迟上架
     */
    @ApiModelProperty("商品上架状态：0：未上架，1：已上架，2：延迟上架")
    private Integer status;
    /**
     * 是否删除
     */
    private Boolean delFlag;

    @ApiModelProperty("标准价格")
    private Double standardPrice;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("面料")
    private String materielInfo;
    @ApiModelProperty("部件")
    private String partInfo;
}
