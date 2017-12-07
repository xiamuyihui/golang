package com.kingthy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author :xumin
 * @Description:
 * @Date:17:43 2017/11/2
 */
@Data
@ToString
public class Area implements Serializable
{
    @javax.persistence.Id
    private Integer id;
    
    @JsonIgnore
    private Date modifyDate;
    
    @JsonIgnore
    private String creator;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createDate;
    
    @JsonIgnore
    private String modifier;
    
    @JsonIgnore
    private Integer version;
    
    @JsonIgnore
    private Integer orders;
    
    private Integer grade;
    
    private String name;
    
    @JsonIgnore
    private String treePath;
    
    private Integer areaParentId;
    
    private String fullName;
    
    private static final long serialVersionUID = 1L;
    

}