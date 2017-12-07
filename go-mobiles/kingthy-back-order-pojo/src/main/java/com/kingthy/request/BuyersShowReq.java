package com.kingthy.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author Created by likejie on 2017/6/8.
 */
@Data
@ToString
public class BuyersShowReq implements Serializable {

    @ApiModelProperty("登录令牌")
    private String token;

    @ApiModelProperty("商品uuid")
    private String goodsUuid;

    @ApiModelProperty("会员业务主键")
    private String memberUuid;

    @ApiModelProperty("页码")
    private Integer pageNo;

    @ApiModelProperty("分页大小，默认10")
    private Integer pageSize;

}
