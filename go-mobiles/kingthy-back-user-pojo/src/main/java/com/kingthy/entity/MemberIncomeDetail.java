package com.kingthy.entity;

import com.kingthy.common.BaseTableFileds;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * MemberIncomeDetail
 * @author xumin
 * @date  2017/6/8.
 */
@Data
@ToString
public class MemberIncomeDetail extends BaseTableFileds implements Serializable {

    private String membersUuid;

    private String goodsUuid;

    private String orderSn;

    private BigDecimal money;

    private Integer quantity;

    private static final long serialVersionUID = 1L;
}