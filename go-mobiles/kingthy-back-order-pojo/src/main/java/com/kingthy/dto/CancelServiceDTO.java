package com.kingthy.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kingthy.common.BaseReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 16:34 on 2017/5/16.
 * @Modified by:
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class CancelServiceDTO extends BaseReq implements Serializable {

    @ApiModelProperty("订单号")
    private String orderSn;

}
