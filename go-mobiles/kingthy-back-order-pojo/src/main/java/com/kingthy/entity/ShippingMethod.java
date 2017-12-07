package com.kingthy.entity;

import com.kingthy.common.BaseTableFileds;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @author:xumin
 * @Description:
 * @Date:18:14 2017/11/2
 */
@Data
@ToString
public class ShippingMethod extends BaseTableFileds implements Serializable {

    /**
     * 排序
     */
    private Integer orders;

    /**
     * 续重
     */
    private Integer continueWeight;

    /**
     * 默认续重价格
     */
    private BigDecimal defaultContinuePrice;

    /**
     * 默认首重价格
     */
    private BigDecimal defaultFirstPrice;

    /**
     * 描述
     */
    private String description;

    /**
     * 首重
     */
    private Integer firstWeight;

    /**
     * log图标
     */
    private String icon;

    /**
     * 名称
     */
    private String name;

    /**
     * 快递公司的业务主键
     */
    private String deliveryCorpUuid;

    /**
     * 状态
     */
    private Integer type;
}