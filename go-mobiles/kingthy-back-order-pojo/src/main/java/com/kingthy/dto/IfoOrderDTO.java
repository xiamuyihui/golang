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
 * @DATE Created by 14:37 on 2017/9/25.
 * @Modified by:
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class IfoOrderDTO extends BaseReq implements Serializable
{
    @ApiModelProperty("订单号")
    private String orderSn;

    @ApiModelProperty("子订单列表")
    private List<IfoOrderItemDTO> orderItemDTO;
}
