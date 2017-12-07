package com.kingthy.entity;

import com.kingthy.common.BaseTableFileds;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 *
 * MemberIncome
 * @author xumin
 * @date  2017/6/8.
 */
@Data
@ToString
public class MemberIncome extends BaseTableFileds implements Serializable {

    private String membersUuid;

    private BigDecimal amount;

    private BigDecimal withdraws;

    private BigDecimal balance;

    private static final long serialVersionUID = 1L;

}