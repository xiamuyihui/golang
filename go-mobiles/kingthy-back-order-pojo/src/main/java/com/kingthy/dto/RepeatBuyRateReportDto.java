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
 * @class_name RepeatBuyRateReportDto
 * @description 重复购买率统计结果出参封装类
 * @create 2017/7/25
 */
@Data
@ToString
public class RepeatBuyRateReportDto implements Serializable{

    private BigDecimal rate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date refreshTime;
}
