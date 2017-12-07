package com.kingthy.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.kingthy.common.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 *
 * 会员收获地址实体类
 * @author likejie
 * @date  2017-4-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class Receiver extends BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty("业务主键")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT uuid_short()")
    private String uuid;
    
    @ApiModelProperty("详细地址")
    private String address;
    
    @ApiModelProperty("地区")
    private String areaName;
    
    @ApiModelProperty("联系人")
    private String consignee;
    
    @ApiModelProperty("是否默认")
    @Column(name = "is_default")
    private Boolean isDefault;
    
    @ApiModelProperty("联系人电话")
    private String phone;
    
    @ApiModelProperty("邮编")
    @Column(name = "zip_code")
    private String zipCode;
    
    @ApiModelProperty("地区主键")
    @Column(name = "area_id")
    private Integer areaId;
    
    @ApiModelProperty("会员的业务编号")
    @Column(name = "member_uuid")
    private String memberUuid;
    
}
