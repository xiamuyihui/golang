package com.kingthy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name AgeGroupBuyDto
 * @description 查询年龄分组购买金额出参封装类
 * @create 2017/7/25
 */
@Data
@ToString
public class AgeGroupBuyDto implements Serializable {

    /**
     *金额
     */

    private BigDecimal money;
    /**
     * 年龄段
     */
    private String ageGroup;
    /**
     * 刷新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date refreshTime;
}
