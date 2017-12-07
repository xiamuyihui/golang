/**
 * 系统项目名称
 * com.kingthy.dto
 * MemberOrderDTO.java
 * 
 * 2017年4月21日-上午11:51:21
 *  2017金昔科技有限公司-版权所有
 *
 */
package com.kingthy.dto;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 *
 * 会员订单对象
 * @author likejie
 * @date  2017/4/24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class MemberOrderDTO implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty("订单主表业务主键")
    private String orderUuid;
    
    @ApiModelProperty("会员业务主键")
    private String memberUuid;
    
    @ApiModelProperty("会员名称")
    private String memberName;
    
    @ApiModelProperty("订单状态")
    private Integer orderStatus;
    
    @ApiModelProperty("订单主表序列")
    private String orderSn;
    
    @ApiModelProperty("订单总金额")
    private Double totalAmount;
    
    @ApiModelProperty("订单总数量")
    private Integer totalQuantity;
    
    @ApiModelProperty("订单从表业务主键")
    private String orderItemUuid;
    
    @ApiModelProperty("订单从表序列")
    private String orderItemSn;
    
    @ApiModelProperty("商品业务主键")
    private String productUuid;
    
    @ApiModelProperty("商品名称")
    private String productName;
    
    @ApiModelProperty("商品图片")
    private String productImage;
    
    @ApiModelProperty("商品价格")
    private Double price;
    
    @ApiModelProperty("商品数量")
    private Integer quantity;
    
    @ApiModelProperty("体型数据")
    private String figureUuid;
    
    @ApiModelProperty("订单创建时间")
    private Date createDate;
    
}
