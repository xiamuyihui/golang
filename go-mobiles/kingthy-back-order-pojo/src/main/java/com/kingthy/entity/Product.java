package com.kingthy.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Id;
/**
 * @author:xumin
 * @Description:
 * @Date:18:04 2017/11/2
 */
public class Product implements Serializable
{
    @Id
    private Long id;
    
    private Date createDate;
    
    private Date modifyDate;
    
    private Long version;
    
    private Integer allocatedStock;
    
    private BigDecimal cost;
    
    private Long exchangePoint;
    
    private Boolean isDefault;
    
    private BigDecimal marketPrice;
    
    private BigDecimal price;
    
    private Long rewardPoint;
    
    private String sn;
    
    private Integer stock;
    
    private Long goodsId;
    
    private String specificationValues;
    
    private static final long serialVersionUID = 1L;
    
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
    
    public Integer getAllocatedStock()
    {
        return allocatedStock;
    }
    
    public void setAllocatedStock(Integer allocatedStock)
    {
        this.allocatedStock = allocatedStock;
    }
    
    public BigDecimal getCost()
    {
        return cost;
    }
    
    public void setCost(BigDecimal cost)
    {
        this.cost = cost;
    }
    
    public Long getExchangePoint()
    {
        return exchangePoint;
    }
    
    public void setExchangePoint(Long exchangePoint)
    {
        this.exchangePoint = exchangePoint;
    }
    
    public Boolean getIsDefault()
    {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault)
    {
        this.isDefault = isDefault;
    }
    
    public BigDecimal getMarketPrice()
    {
        return marketPrice;
    }
    
    public void setMarketPrice(BigDecimal marketPrice)
    {
        this.marketPrice = marketPrice;
    }
    
    public BigDecimal getPrice()
    {
        return price;
    }
    
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }
    
    public Long getRewardPoint()
    {
        return rewardPoint;
    }
    
    public void setRewardPoint(Long rewardPoint)
    {
        this.rewardPoint = rewardPoint;
    }
    
    public String getSn()
    {
        return sn;
    }
    
    public void setSn(String sn)
    {
        this.sn = sn == null ? null : sn.trim();
    }
    
    public Integer getStock()
    {
        return stock;
    }
    
    public void setStock(Integer stock)
    {
        this.stock = stock;
    }
    
    public Long getGoodsId()
    {
        return goodsId;
    }
    
    public void setGoodsId(Long goodsId)
    {
        this.goodsId = goodsId;
    }
    
    public String getSpecificationValues()
    {
        return specificationValues;
    }
    
    public void setSpecificationValues(String specificationValues)
    {
        this.specificationValues = specificationValues == null ? null : specificationValues.trim();
    }
}