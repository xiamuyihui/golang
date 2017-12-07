package com.kingthy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Date;

/**
 * 会员 [实体类]
 *
 * @author likejie 2017-4-21
 * @version 1.0.0
 */
@Data
@ToString
public class Member implements Serializable
{
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("业务主键")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT uuid_short()")
    private String uuid;

    @ApiModelProperty("是否删除(0:否，1：是)")
    @JsonIgnore
    private Boolean delFlag;

    @ApiModelProperty("创建时间")
    @JsonIgnore
    private Date createDate;

    @ApiModelProperty("更新时间")
    @JsonIgnore
    private Date modifyDate;

    @ApiModelProperty("创建人")
    @JsonIgnore
    private String creator;

    @ApiModelProperty("最后修改人")
    @JsonIgnore
    private String modifier;

    @ApiModelProperty("版本")
    @JsonIgnore
    private Integer version;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("密码")
    private String passWord;

    @ApiModelProperty("支付密码")
    private String paymentPassword;

    @ApiModelProperty("支付密码加密钥")
    private String paymenSalt;

    @ApiModelProperty("盐")
    private String salt;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("性别")
    private Integer gender;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("地址主键拼接")
    private String areaIds;

    @ApiModelProperty("地区名称")
    private String areaName;

    @ApiModelProperty("生日")
    private Date birth;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("邮编")
    private String zipCode;

    @JsonIgnore
    @ApiModelProperty("锁定时长")
    private Date lockedDate;

    @JsonIgnore
    @ApiModelProperty("登录日期")
    private Date loginDate;

    @JsonIgnore
    @ApiModelProperty("登录失败次数")
    private Integer loginFailureCount;

    @JsonIgnore
    @ApiModelProperty("登录IP")
    private String loginIp;

    @JsonIgnore
    @ApiModelProperty("第三方登录插件")
    private String loginPluginId;

    @ApiModelProperty("积分")
    private Integer integral;

    @JsonIgnore
    @ApiModelProperty("注册IP")
    private String registerIp;

    @ApiModelProperty("是否禁用")
    private Boolean isEnabled;

    @ApiModelProperty("是否锁定")
    private Boolean isLocked;

    @ApiModelProperty("是否为设计师")
    private Boolean isDesinger;

    @ApiModelProperty("令牌")
    private String token;

    @JsonIgnore
    @ApiModelProperty("最后登录时间")
    private Date lastLoginDate;

    @ApiModelProperty("用户头像")
    private String headImage;

    @ApiModelProperty("二维码")
    private String barCodes;

    @ApiModelProperty("星座")
    private String constellation;

    @ApiModelProperty("职业")
    private String occupation;

    @ApiModelProperty("着装风格")
    private String style;

    @ApiModelProperty("肤色")
    private String skinColor;

    @ApiModelProperty("脸型")
    private String face;

    @ApiModelProperty("签名")
    private String signature;

    @ApiModelProperty("工作")
    private String job;

}
