/**
 * 系统项目名称
 * com.kingthy.dto
 * MemberReceiverDTO.java
 * 
 * 2017年4月21日-下午4:17:46
 *  2017金昔科技有限公司-版权所有
 *
 */
package com.kingthy.dto;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 *
 * 会员收获地址DTO
 * @author likejie
 * @date  2017/4/24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class MemberReceiverDTO implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty("业务主键")
    private String uuid;
    
    @ApiModelProperty("会员的业务编号")
    private String memberUuid;
    
    @ApiModelProperty("收货人")
    private String consignee;
    
    @ApiModelProperty("联系人电话")
    private String phone;
    
    @ApiModelProperty("地区主键")
    private Integer areaId;
    
    @ApiModelProperty("所在地区")
    private String areaName;
    
    @ApiModelProperty("详细地址")
    private String address;
    
    @ApiModelProperty("邮编")
    private String zipCode;
    
    @ApiModelProperty("是否默认")
    private Boolean isDefault;
    
}
