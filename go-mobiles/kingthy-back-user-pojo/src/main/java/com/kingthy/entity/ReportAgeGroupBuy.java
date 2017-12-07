package com.kingthy.entity;

import com.kingthy.common.BaseTableFileds;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * ReportAgeGroupBuy
 * @author CZ
 * @date  2017-4-20
 */
@ToString
@Data
@EqualsAndHashCode(callSuper = true)
public class ReportAgeGroupBuy extends BaseTableFileds implements Serializable{

    private Boolean status;
    /**金额*/
    private BigDecimal money;
    /**年龄段*/
    private String ageGroup;

}