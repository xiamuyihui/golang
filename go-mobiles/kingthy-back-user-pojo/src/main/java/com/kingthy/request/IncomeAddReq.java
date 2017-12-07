package com.kingthy.request;

import com.kingthy.common.BaseReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;


/**
 *
 * DownloadFileReq
 * @author xumin
 * @date  2017/9/11.
 *
 */
@Data
@ToString
public class IncomeAddReq extends BaseReq implements java.io.Serializable{

    @ApiModelProperty("订单号")
    private String orderSn;

    @ApiModelProperty("会员uuid")
    private String memberUuid;

    private String goodsUuid;

    private Integer quantity;

    private  BigDecimal amount;
}
