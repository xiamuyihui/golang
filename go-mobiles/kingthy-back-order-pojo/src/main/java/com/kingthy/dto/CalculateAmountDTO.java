package com.kingthy.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
/**
 * @author:xumin
 * @Description:
 * @Date:17:44 2017/11/2
 */
@Data
@ToString
@AllArgsConstructor
public class CalculateAmountDTO
{
    /**
     * 商品价格
     */
    private BigDecimal price;
    
    /**
     * 支付手续费
     */
    private BigDecimal fee;
    
    /**
     * 运费
     */
    private BigDecimal freight;
    
    /**
     * 税金
     */
    private BigDecimal tax;
    
    /**
     * 促销折扣
     */
    private BigDecimal promotionDiscount;
    
    /**
     * 优惠券折扣
     */
    private BigDecimal couponDiscount;
    
    /**
     * 调整金额
     */
    private BigDecimal offsetAmount;
}
