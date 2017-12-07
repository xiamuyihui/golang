package com.kingthy.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 *
 * 描述：提现
 * @author xumin
 * @date  2017/6/12.
 */
@Data
@ToString
public class WithDrawsCashDTO implements Serializable {

    @ApiModelProperty("0:申请提现 1:提现中 2:提示成功")
    private Integer status;

    private BigDecimal money;

    private Date createDate;
}
