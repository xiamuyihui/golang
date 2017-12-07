package com.kingthy.entity;

import com.kingthy.common.BaseTableFileds;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;


/**
 *
 * ReportUserData
 * @author CZ
 * @date  2017-4-20
 */
@ToString
@Data
@EqualsAndHashCode(callSuper = true)
public class ReportUserData extends BaseTableFileds implements Serializable{


    private Boolean status;
    /**用户量*/
    private Integer number;
    /**数据类型*/
    private Integer dataType;
    /**时间段类型*/
    private Integer timeType;


}