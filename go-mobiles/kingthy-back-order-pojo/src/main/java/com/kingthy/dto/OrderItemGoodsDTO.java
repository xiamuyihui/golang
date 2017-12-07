package com.kingthy.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author Created by xumin on 2017/4/24.
 */
@Data
@ToString
public class OrderItemGoodsDTO implements java.io.Serializable{

    private Double price;
    private Integer quantity;
    private String goodsName;
    @ApiModelProperty("部件")
    private String partInfo;
    @ApiModelProperty("图片")
    private String goodsImage;
    @ApiModelProperty("面料")
    private String materielInfo;
    @ApiModelProperty("商品ID")
    private String goodsUuid;
}
