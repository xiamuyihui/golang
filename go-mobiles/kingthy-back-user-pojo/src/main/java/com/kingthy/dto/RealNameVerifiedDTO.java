package com.kingthy.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 *
 * 银联-实名认证
 * @author  likejie on 2017/6/21.
 */
@Data
@ToString
public class RealNameVerifiedDTO {

    @JsonIgnore
    @ApiModelProperty("用户uuid")
    private String memberUuid;

    @ApiModelProperty("证件号码")
    private String certifId;

    @ApiModelProperty("客户名称")
    private String customerNm;

    @ApiModelProperty("银行卡号")
    private String accNo;

    @ApiModelProperty("手机号")
    private String phone;
}
