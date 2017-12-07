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
 * @DATE Created by 15:35 on 2017/11/21.
 * @Modified by:
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class IfoFullOrderDTO extends BaseReq implements Serializable
{
    @ApiModelProperty("子订单信息")
    private List<IfoFullOrderItemDTO> itemDTOList;
}
