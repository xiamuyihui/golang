package com.kingthy.dto;

import com.kingthy.common.BaseReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 13:59 on 2017/5/16.
 * @Modified by:
 */

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class AfterSaleServiceDTO extends BaseReq implements Serializable {

    @ApiModelProperty("申请服务 0:换货 1仅退货 2退货退款")
    private Integer applyServiceType;

    @ApiModelProperty("换货原因")
    private String exchangeReason;

    @ApiModelProperty("换货说明")
    private String memo;

    @ApiModelProperty("图片url")
    private List<String> listImg;

    @ApiModelProperty("订单号")
    private String orderSn;
}
