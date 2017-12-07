/**
 * 系统项目名称
 * com.kingthy.dto
 * FansDTO.java
 * 
 * 2017年5月12日-上午9:28:32
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
 * 粉丝
 * @author likejie
 * @date  2017/5/12.
 */
@Data
@ToString
public class FansDTO implements Serializable
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
    
}
