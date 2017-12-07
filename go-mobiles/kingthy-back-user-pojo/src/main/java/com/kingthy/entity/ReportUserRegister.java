package com.kingthy.entity;

import com.kingthy.common.BaseTableFileds;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * ReportUserRegister
 * @author CZ
 * @date  2017-4-20
 */
@ToString
@Data
@EqualsAndHashCode(callSuper = true)
public class ReportUserRegister extends BaseTableFileds implements Serializable{


    private Boolean status;
    /**注册用户数*/
    private Integer number;
    /**注册日期*/
    private Date registerDate;


}