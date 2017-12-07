package com.kingthy.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 个人中心首页展示数据
 * 
 * @author likejie
 *
 */
@Data
@ToString
@ApiModel(value = "个人中心-首页", description = "个人中心首页展示数据")
public class MemberHomeDTO implements Serializable
{
    
    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     *
     * @since 1.0.0
     */
    
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty("会员业务主键")
    private String memberUuid;
    
    @ApiModelProperty("会员昵称")
    private String nickName;
    
    @ApiModelProperty("会员头像")
    private String headImage;

    
}
