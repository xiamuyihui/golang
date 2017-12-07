package com.kingthy.entity;

import com.kingthy.common.BaseTableFileds;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 *
 *
 * 促销实体类
 *
 * @author chenz 2017年8月8日 下午7:42:35
 *
 * @version 1.0.0
 *
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class OperateSalesPromotion extends BaseTableFileds implements Serializable{


    private Integer status;

    private String activityName;

    private Integer policyType;

    private String policy;

    private Date startTime;

    private Date endTime;

    private Integer joinGoods;

    private String description;


}