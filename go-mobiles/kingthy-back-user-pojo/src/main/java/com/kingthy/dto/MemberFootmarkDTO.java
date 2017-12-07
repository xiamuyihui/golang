/**
 * 系统项目名称
 * com.kingthy.dto
 * MemberFootmarkDTO.java
 * 
 * 2017年4月24日-下午3:07:32
 *  2017金昔科技有限公司-版权所有
 *
 */
package com.kingthy.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


/**
 *
 * 我的足迹
 * @author likejie
 * @date  2017/4/24
 */
@Data
@ToString
@ApiModel(value = "我的足迹", description = "会员浏览的商品")
public class MemberFootmarkDTO implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty("业务编号")
    private String uuid;
    
    @ApiModelProperty("商品业务编号")
    private String productUuid;
    
    @ApiModelProperty("商品名称")
    private String goodsName;
    
    @ApiModelProperty("商品图片")
    private String goodsImage;
    
    @ApiModelProperty("商品描述")
    private String goodsDetails;
    
    @ApiModelProperty("设计师")
    private String desinger;
    
    @ApiModelProperty("参考价")
    private BigDecimal standardPrice;
    
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    
}
