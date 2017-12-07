package com.kingthy.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 
 * 会员
 * 
 * @author likejie on 2017/4/25.
 */
@Data
@ToString
public class MemberDTO implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty("业务主键")
    private String uuid;
    
    @ApiModelProperty("用户名")
    private String userName;
    
    @ApiModelProperty("昵称")
    private String nickName;
    
    @ApiModelProperty("地址")
    private String address;
    
    @ApiModelProperty("地区主id")
    private String areaIds;
    
    @ApiModelProperty("地区名称")
    private String areaName;
    
    @ApiModelProperty("生日")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birth;
    
    @ApiModelProperty("邮箱")
    private String email;
    
    @ApiModelProperty("手机号")
    private String phone;
    
    @ApiModelProperty("是否为设计师")
    private Boolean isDesinger;

    @JsonIgnore
    @ApiModelProperty("令牌")
    private String token;
    
    @ApiModelProperty("用户头像")
    private String headImage;
    
    @ApiModelProperty("职业")
    private String occupation;
    
    @ApiModelProperty("着装风格")
    private String style;
    
    @ApiModelProperty("肤色")
    private String skinColor;
    
    @ApiModelProperty("脸型")
    private String face;
    
    @ApiModelProperty("个性签名")
    private String signature;
    
    @ApiModelProperty("工作")
    private String job;

    @ApiModelProperty("是否已设置支付密码")
    private Boolean isSetPaymentPwd;


    @ApiModelProperty("绑定的银行卡")
    private String bankCard;

    @ApiModelProperty("绑定的银行")
    private String bankName;
    
}
