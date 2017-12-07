package com.kingthy.page;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 排序
 *
 * Order
 * 
 * 潘勇 2017年3月6日 下午1:58:23
 * 
 * @version 1.0.0
 *
 */
public class Order implements Serializable
{
    
    private static final long serialVersionUID = -3078342809727773232L;
    
    /**
     * 方向
     */
    public enum Direction
    {
        
        /** 递增 */
        asc,
        
        /** 递减 */
        desc
    }
    
    /** 默认方向 */
    private static final Order.Direction DEFAULT_DIRECTION = Order.Direction.desc;
    
    /** 属性 */
    private String property;
    
    /** 方向 */
    private Order.Direction direction = DEFAULT_DIRECTION;
    
    /**
     * 构造方法
     */
    public Order()
    {
    }
    
    /**
     * 构造方法
     * 
     * @param property 属性
     * @param direction 方向
     */
    public Order(String property, Order.Direction direction)
    {
        this.property = property;
        this.direction = direction;
    }
    
    /**
     * 返回递增排序
     * 
     * @param property 属性
     * @return 递增排序
     */
    public static Order asc(String property)
    {
        return new Order(property, Order.Direction.asc);
    }
    
    /**
     * 返回递减排序
     * 
     * @param property 属性
     * @return 递减排序
     */
    public static Order desc(String property)
    {
        return new Order(property, Order.Direction.desc);
    }
    
    /**
     * 获取属性
     * 
     * @return 属性
     */
    public String getProperty()
    {
        return property;
    }
    
    /**
     * 设置属性
     * 
     * @param property 属性
     */
    public void setProperty(String property)
    {
        this.property = property;
    }
    
    /**
     * 获取方向
     * 
     * @return 方向
     */
    public Order.Direction getDirection()
    {
        return direction;
    }
    
    /**
     * 设置方向
     * 
     * @param direction 方向
     */
    public void setDirection(Order.Direction direction)
    {
        this.direction = direction;
    }
    
    /**
     * 重写equals方法
     * 
     * @param obj 对象
     * @return 是否相等
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        if (this == obj)
        {
            return true;
        }
        Order other = (Order)obj;
        return new EqualsBuilder().append(getProperty(), other.getProperty())
            .append(getDirection(), other.getDirection())
            .isEquals();
    }
    
    /**
     * 重写hashCode方法
     * 
     * @return HashCode
     */
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37).append(getProperty()).append(getDirection()).toHashCode();
    }
    
}