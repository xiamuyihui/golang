package com.kingthy.common;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BaseEntity extends BaseReq implements Serializable
{
    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     *
     * @since 1.0.0
     */
    
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @Id
    @Column(name = "id")
    private Integer id;
    
    @JsonIgnore
    private Date createDate;
    
    @JsonIgnore
    private Date modifyDate;
    
    @JsonIgnore
    private String creator;
    
    @JsonIgnore
    private String modifier;
    
    @JsonIgnore
    private Integer version;
    
    @JsonIgnore
    private Boolean delFlag;
}
