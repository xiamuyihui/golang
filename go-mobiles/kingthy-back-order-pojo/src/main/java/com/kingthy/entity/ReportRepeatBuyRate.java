package com.kingthy.entity;


import com.kingthy.common.BaseTableFileds;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * @author:xumin
 * @Description:
 * @Date:18:14 2017/11/2
 */
@ToString
@Data
@EqualsAndHashCode(callSuper = true)
public class ReportRepeatBuyRate extends BaseTableFileds implements Serializable{


    private Boolean status;
    /**
     * 重复购买率
     */
    private BigDecimal rate;
}