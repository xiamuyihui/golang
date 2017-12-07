/**
 * 系统项目名称
 * com.kingthy.request
 * MemberOrderReq.java
 * 
 * 2017年5月22日-下午4:44:33
 *  2017金昔科技有限公司-版权所有
 *
 */
package com.kingthy.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


/**
 *
 * MemberOrderReq
 * @author likejie
 * @date  2017/5/22.
 *
 */
@Data
@ToString
public class MemberOrderReq implements Serializable
{
    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     *
     * @since 1.0.0
     */
    
    private static final long serialVersionUID = 1L;
    
    /*** 登录令牌 *****/
    @ApiModelProperty("登录令牌")
    private String token;

    @JsonIgnore
    @ApiModelProperty("会员业务主键")
    private String memberUuid;
    
    /** 订单状态 ***/
    @ApiModelProperty("订单状态")
    private Integer orderStatus;

    /** 分页大小 ***/
    @ApiModelProperty("分页大小")
    private Integer pageNum;
    
    /** 分页大小 ***/
    @ApiModelProperty("分页大小")
    private Integer pageSize;
    
}
