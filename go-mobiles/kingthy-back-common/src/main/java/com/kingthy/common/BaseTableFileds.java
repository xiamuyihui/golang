/**
 * 系统项目名称
 * com.kingthy.platform.util
 * BaseTableFileds.java
 * 
 * 2017年4月10日-下午4:51:35
 *  2017金昔科技有限公司-版权所有
 *
 */
package com.kingthy.common;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

/**
 *
 * BaseTableFileds
 * 
 * yuanml 2017年4月10日 下午4:51:35
 * 
 * @version 1.0.0
 *
 */
@ToString
@Data
public class BaseTableFileds implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 表主键
     */
    @JsonIgnore
    private Integer id;
    
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date createDate;
    
    /**
     * 创建人
     */
    private String creator;
    
    /**
     * 最后修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date modifyDate;
    
    /**
     * 最后修改人
     */
    private String modifier;
    
    /**
     * 版本
     */
    @JsonIgnore
    private Integer version;
    
    /**
     * 删除标识
     */
    @JsonIgnore
    private Boolean delFlag;
    
    /**
     * 业务主键
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid_short()")
    private String uuid;
    
}
