package com.kingthy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name OrderAreaReportDto
 * @description 地区订单分布统计结果出参封装类
 * @create 2017/7/25
 */
@Data
@ToString
public class OrderAreaReportDto implements Serializable {
    private Integer num;
    private String province;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date refreshTime;
}
