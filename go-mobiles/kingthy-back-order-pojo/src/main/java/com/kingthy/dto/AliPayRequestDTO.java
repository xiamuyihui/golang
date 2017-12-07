package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

/**
 * 支付数据传输类
 *
 * @author AliPayRequestDTO
 * 
 * 潘勇 2017年1月13日 下午5:50:12
 * 
 * @version 1.0.0
 *
 */
@Data
@ToString
public class AliPayRequestDTO
{
    /**
     * 交易单号
     */
    private String outTradeNo;
    
    /**
     * 商品名称
     */
    private String subject;
    
    /**
     * 商品详情
     */
    private String body;
    
    /**
     * 商品金额
     */
    private Double totalFee;
    
    /**
     * 客户端来源
     */
    private String appenv;
    
    /**
     * 扩展参数
     */
    private String outContext;
}
