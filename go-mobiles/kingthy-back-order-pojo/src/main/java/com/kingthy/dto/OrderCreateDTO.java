package com.kingthy.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
/**
 * @author:xumin
 * @Description:
 * @Date:18:04 2017/11/2
 */
@Data
@ToString
public class OrderCreateDTO implements Serializable
{

    /**
     * 商品ID
     */
    @ApiModelProperty("商品ID")
    private String goodsUuId;

    /**
     * 数量
     */
    @ApiModelProperty("商品数量")
    private Integer quantity;

    /**
     * 体型表的业务主键
     */
    @ApiModelProperty("体型ID")
    private String figureUuid;

    @ApiModelProperty("备注")
    private String memo;

}
