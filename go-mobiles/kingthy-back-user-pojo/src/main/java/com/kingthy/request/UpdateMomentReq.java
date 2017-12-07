package com.kingthy.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 *
 * UpdateMomentReq
 * @author 赵生辉
 * @date 2017年08月09日 15:03
 *
 */
@Data
@ToString
public class UpdateMomentReq implements Serializable
{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "业务主键")
    private String uuid;

    @ApiModelProperty(name = "审核状态")
    private Integer review;

    @ApiModelProperty(name = "原因")
    private String reason;

    @ApiModelProperty(name = "修改人")
    private String modifier;
}
