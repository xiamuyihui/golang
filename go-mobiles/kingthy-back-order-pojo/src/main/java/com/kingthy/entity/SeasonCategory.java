package com.kingthy.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;
import lombok.ToString;
/**
 * @author:xumin
 * @Description:
 * @Date:18:14 2017/11/2
 */
@Data
@ToString
public class SeasonCategory implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer id;

    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid_short()")
    private String uuid;

    private Date createDate;

    private Date modifyDate;

    private Integer version;

    private String creator;

    private String modifier;

    private Boolean delFlag;

    private String parentId;
    
    private String className;
    
    private Boolean status;
    
    private String description;
    
    private Integer orders;
    
    private Integer grade;
    
    private String treePath;
    

    
}