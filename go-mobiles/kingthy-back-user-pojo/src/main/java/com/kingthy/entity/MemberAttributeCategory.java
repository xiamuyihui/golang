package com.kingthy.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.kingthy.common.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 *
 * 会员属性类别 [实体类]
 * 
 * @author likejie 2017-5-4
 * 
 * @version 1.0.0
 *
 */
@Data
@ToString
public class MemberAttributeCategory extends BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty("业务编号")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT uuid_short()")
    private String uuid;
    
    @ApiModelProperty("父及业务编号")
    @Column(name = "parent_uuid")
    private String parentUuid;
    
    @ApiModelProperty("名称")
    private String attributeName;
    
    @ApiModelProperty("类型(1：肤色，2：发型，3：工作，4：爱好)")
    private Integer attributeType;
    
    @ApiModelProperty("图片")
    private String attributeImage;
    
    @ApiModelProperty("备注")
    private String remark;
    
}
