package com.kingthy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name EverydayRegisterNumDto
 * @description 每日注册用户数量封装类
 * @create 2017/7/19
 */
@Data
@ToString
public class EverydayRegisterNumDto implements Serializable{
    private Integer number;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
}
