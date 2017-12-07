/**
 * 系统项目名称
 * com.kingthy.dto
 * SimliarProductDTO.java
 * 
 * 2017年5月4日-下午3:49:59
 *  2017金昔科技有限公司-版权所有
 *
 */
package com.kingthy.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


/**
 *
 * 相似商品
 * @author likejie
 * @date  2017/5/8.
 */
@Data
@ToString
public class SimliarProductDTO implements Serializable
{
    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     *
     * @since 1.0.0
     */
    
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty("业务主键")
    private String uuid;
    
    @ApiModelProperty("商品名称")
    private String goodsName;
    
    @ApiModelProperty("商品卖点")
    private String goodsFeature;
    
    @ApiModelProperty("标准价格")
    private String standardPrice;
    
    @ApiModelProperty("设计师")
    private String desinger;
    
    @ApiModelProperty("商品图片")
    private String goodsImage;
}
