package com.kingthy.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * SelectGoodsByWarehouseReq(描述其作用)
 * <p>
 * @author 赵生辉 2017年06月27日 16:45
 *
 * @version 1.0.0
 */
@Data
@ToString
public class SelectGoodsByWarehouseReq implements Serializable
{
    @ApiModelProperty("用户token")
    private String token;

    @ApiModelProperty("商品状态")
    private int status;

    @ApiModelProperty("当前页数")
    private int pageNo;

    @ApiModelProperty("每页显示的数量")
    private int pageSize;
}
