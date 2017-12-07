package com.kingthy.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.ToString;
/**
 * @author:xumin
 * @Description:
 * @Date:18:04 2017/11/2
 */
@Data
@ToString
public class Orders implements Serializable
{
    private Integer id;
    
    private Date createDate;
    
    private Date modifyDate;

    private Date paymentDate;

    private Integer version;
    
    private String address;
    
    private BigDecimal amount;
    
    private String areaName;
    
    private Date completeDate;
    
    private String consignee;
    
    private BigDecimal couponDiscount;
    
    private Integer exchangePoint;
    
    private Date expire;
    
    private BigDecimal fee;
    
    private BigDecimal freight;
    
    private String invoiceContent;
    
    private String invoiceTitle;

    /**
     * 是否需要发票
     */
    private Boolean isInvoice;
    
    private Boolean isExchangePoint;
    
    private Boolean isUseCouponCode;
    
    private String memo;
    
    private BigDecimal offsetAmount;
    
    private Integer paymentMethodType;
    
    private String phone;
    
    private BigDecimal price;
    
    private Integer quantity;
    
    private BigDecimal refundAmount;
    
    private Integer returnedQuantity;
    
    private Integer shippedQuantity;
    
    private String shippingMethodName;
    
    private String shippingNumber;
    
    private String sn;
    
    private Integer status;
    
    private BigDecimal tax;
    
    private Integer type;
    
    private String zipCode;
    
    private Integer areaUuid;
    
    private String couponUuid;
    
    private String memberName;

    private String memberUuid;

    private String paymentMethodUuid;
    
    private String shippingMethodUuid;
    
    private Boolean delFlag;

    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY, generator = "SELECT uuid_short()")
    private String uuid;
    
    private String figureUuid;
    
    private String creator;
    
    private String modifier;
    
    private Integer provinceUuid;
    
    private String provinceName;
    
    private Integer cityUuid;
    
    private String cityName;

    /**
     * 发票类型 0: 个人 1公司
     */
    private Integer invoiceType;
    
    private static final long serialVersionUID = 1L;

    /**
     * 状态
     */
    public enum OrderStatus
    {
        
        /** 等待付款 */
        pendingPayment(0, "待付款"),

        /** 生产中 */
        producing(1, "生产中"),

        /** 等待发货 */
        pendingShipment(2, "待发货"),

        /** 待签收 */
        goodsReceipt(3, "待签收"),

        /** 待评价 */
        comment(4, "待评价"),

        /** 已取消 */
        canceled(5, "已取消"),

        /** 待退单 */
        refund(6, "待退单"),

        /** 已完成 */
        completed(7, "已完成");

        private int value;
        
        private String nameValue;
        
        public int getValue()
        {
            return value;
        }
        
        public void setValue(int value)
        {
            this.value = value;
        }
        
        private OrderStatus(int value, String name)
        {
            this.value = value;
            this.nameValue = name;
        }
        
        public String getNameValue()
        {
            return nameValue;
        }
        
        public void setNameValue(String nameValue)
        {
            this.nameValue = nameValue;
        }

        public static String keys(int key){
            Map<Integer, String> enumMap = new HashMap(OrderStatus.values().length);
            for (Orders.OrderStatus status: Orders.OrderStatus.values()) {
                enumMap.put(status.getValue(), status.getNameValue());
            }

            return enumMap.get(key);
        }

    }
    
    /**
     * 类型
     */
    public enum OrderType
    {
        /**
         * 普通订单
         */
        general(0),
        /**
         * 手机APP订单
         */
        wap(1),
        /**
         * ipad订单
         */
        ipad(2),
        /**
         * 网站订单
         */
        web(3);
        private int value;
        
        public int getValue()
        {
            return value;
        }
        
        public void setValue(int value)
        {
            this.value = value;
        }
        
        private OrderType(int value)
        {
            this.value = value;
        }

    }

}