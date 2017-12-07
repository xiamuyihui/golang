package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name MemberUuidGroupBuyDto
 * @description 用户消费金额封装类
 * @create 2017/8/28
 */
@Data
@ToString
public class MemberUuidGroupBuyDto implements Serializable{
    private String memberUuid;
    private BigDecimal money;
}
