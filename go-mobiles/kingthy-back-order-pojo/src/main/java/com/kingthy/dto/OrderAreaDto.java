package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name OrderAreaDto
 * @description 订单地区分布出参封装类
 * @create 2017/7/20
 */
@Data
@ToString
public class OrderAreaDto implements Serializable{

    private Integer num;
    private String province;
}
