package com.kingthy.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * PointLog
 * @author none
 * @date  2017/6/8.
 */
public class PointLog implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private Long id;
    
    private Date createDate;
    
    private Date modifyDate;
    
    private Long version;
    
    private Long balance;
    
    private Long credit;
    
    private Long debit;
    
    private String memo;
    
    private String operator;
    
    private Long memberId;
    
    /**
     * 类型
     */
    public enum Type
    {
        
        /** 积分赠送 */
        reward,
        
        /** 积分兑换 */
        exchange,
        
        /** 积分兑换撤销 */
        undoExchange,
        
        /** 积分调整 */
        adjustment
    }
    
    /** 类型 */
    private PointLog.Type type;
    
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
    
    public Long getBalance()
    {
        return balance;
    }
    
    public void setBalance(Long balance)
    {
        this.balance = balance;
    }
    
    public Long getCredit()
    {
        return credit;
    }
    
    public void setCredit(Long credit)
    {
        this.credit = credit;
    }
    
    public Long getDebit()
    {
        return debit;
    }
    
    public void setDebit(Long debit)
    {
        this.debit = debit;
    }
    
    public String getMemo()
    {
        return memo;
    }
    
    public void setMemo(String memo)
    {
        this.memo = memo == null ? null : memo.trim();
    }
    
    public String getOperator()
    {
        return operator;
    }
    
    public void setOperator(String operator)
    {
        this.operator = operator == null ? null : operator.trim();
    }
    
    public PointLog.Type getType()
    {
        return type;
    }
    
    /**
     * 设置类型
     * 
     * @param type 类型
     */
    public void setType(PointLog.Type type)
    {
        this.type = type;
    }
    
    public Long getMemberId()
    {
        return memberId;
    }
    
    public void setMemberId(Long memberId)
    {
        this.memberId = memberId;
    }
}