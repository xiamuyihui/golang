package com.kingthy.dto;

import com.kingthy.common.BaseReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 9:38 on 2017/10/11.
 * @Modified by:
 */

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class NcpltInfoDTO extends BaseReq implements Serializable
{
    @ApiModelProperty("订单号")
    private String orderItemSn;

    @ApiModelProperty("款式编码")
    private String styleCode;

    @ApiModelProperty("物料编码")
    private String materialCode;

    @ApiModelProperty("幅宽")
    private BigDecimal breadth;
}
