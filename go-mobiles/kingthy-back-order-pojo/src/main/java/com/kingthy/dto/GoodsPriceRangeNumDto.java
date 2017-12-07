package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name GoodsPriceRangeNumDto
 * @description 上架商品价格分布出参封装类
 * @create 2017/7/27
 */
@Data
@ToString
public class GoodsPriceRangeNumDto implements Serializable{

    private Integer num;
    private String priceRange;
}
