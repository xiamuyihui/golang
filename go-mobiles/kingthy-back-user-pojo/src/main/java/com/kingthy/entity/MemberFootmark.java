package com.kingthy.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kingthy.common.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * 我的足迹 [实体类]
 * 
 * @author likejie 2017-4-21
 * 
 * @version 1.0.0
 *
 */
@Data
@ToString
public class MemberFootmark   implements Serializable
{
    
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("业务主键")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT uuid_short()")
    private String uuid;
    
    @ApiModelProperty("会员业务主键")
    private String memberUuid;
    
    @ApiModelProperty("商品业务主建")
    private String productUuid;

    @JsonIgnore
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
    
}
