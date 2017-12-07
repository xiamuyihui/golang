/**
 * 系统项目名称
 * com.kingthy.dto
 * AttentionMemberDTO.java
 * 
 * 2017年5月4日-下午2:41:45
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
 * 关注的用户
 * @author likejie
 * @date 2017.5.4
 */
@Data
@ToString
public class AttentionMemberDTO implements Serializable
{
    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     *
     * @since 1.0.0
     */
    
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty("用户业务主键")
    private String memberUuid;
    
    @ApiModelProperty("昵称")
    private String nickName;
    
    @ApiModelProperty("用户名")
    private String userName;
    
    @ApiModelProperty("头像")
    private String headImage;
    
    @ApiModelProperty("粉丝数量")
    private String fansCount;

    @ApiModelProperty("是否互相关注")
    private Boolean isMutualAttention;

    @ApiModelProperty("手机号码")
    private String phone;
}
