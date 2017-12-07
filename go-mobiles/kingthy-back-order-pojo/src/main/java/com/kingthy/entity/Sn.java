package com.kingthy.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * @author:xumin
 * @Description:
 * @Date:18:14 2017/11/2
 */
public class Sn implements Serializable
{
    private Long id;
    
    private Date createDate;
    
    private Date modifyDate;
    
    private Long version;
    
    private Long lastValue;
    
    private Integer type;
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 类型
     */
    public enum Type
    {
        /** 商品编号 */
        goods(0),
        
        /** 订单编号 */
        order(1),
        
        /** 收款单 */
        payment(2),
        
        /** 退款单 */
        refunds(3),
        
        /** 发货单 */
        shipping(4),
        
        /** 产品编号 */
        product(5),
        /** 作品编号 */
        opus(6),
        
        shoppingCat(7), BALANCE(8),

        /*面料编号*/
        accessories(9),

        /*辅料编号*/
        material(10)
        ;
        private int value;
        
        public int getValue()
        {
            return value;
        }
        
        public void setValue(int value)
        {
            this.value = value;
        }
        
        private Type(int value)
        {
            this.value = value;
        }
    }
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public Date getCreateDate()
    {
        return createDate;
    }
    
    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }
    
    public Date getModifyDate()
    {
        return modifyDate;
    }
    
    public void setModifyDate(Date modifyDate)
    {
        this.modifyDate = modifyDate;
    }
    
    public Long getVersion()
    {
        return version;
    }
    
    public void setVersion(Long version)
    {
        this.version = version;
    }
    
    public Long getLastValue()
    {
        return lastValue;
    }
    
    public void setLastValue(Long lastValue)
    {
        this.lastValue = lastValue;
    }
    
    public Integer getType()
    {
        return type;
    }
    
    public void setType(Integer type)
    {
        this.type = type;
    }
}