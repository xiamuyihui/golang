package com.kingthy.entity;

import com.kingthy.common.BaseTableFileds;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
/**
 * @author:xumin
 * @Description:
 * @Date:18:14 2017/11/2
 */
@ToString
@Data
@EqualsAndHashCode(callSuper = true)
public class ReportOrderArea extends BaseTableFileds implements Serializable{

    private Boolean status;
    /**
     * 数量
     */
    private Integer num;
    /**
     * 省份代号
     */
    private String province;


}