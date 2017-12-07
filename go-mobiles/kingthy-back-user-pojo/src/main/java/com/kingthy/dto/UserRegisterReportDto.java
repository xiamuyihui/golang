package com.kingthy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name UserRegisterReportDto
 * @description 过去一周每日用户注册量统计结果出参封装类
 * @create 2017/7/25
 */
@Data
@ToString
public class UserRegisterReportDto implements Serializable{
    private Integer number;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date refreshTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date registerDate;
}
