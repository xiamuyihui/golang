/**
 * 系统项目名称
 * com.kingthy.dto
 * DessingPreferenceCategoryDTO.java
 * 
 * 2017年4月24日-下午6:19:44
 *  2017金昔科技有限公司-版权所有
 *
 */
package com.kingthy.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


/**
 *
 * 着装分类DTO
 * @author likejie
 * @date  2017/4/24
 */
@Data
@ToString
@ApiModel(value = "会员属性分类", description = "会员属性类型数据")
public class MemberAttributeCategoryDTO
{
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
    
    @ApiModelProperty("子类别")
    private List<MemberAttributeCategoryDTO> childrens;
    
}
