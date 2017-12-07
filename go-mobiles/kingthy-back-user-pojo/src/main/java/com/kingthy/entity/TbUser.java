package com.kingthy.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * TbUser
 *
 * @author NONE
 *
 * @version 1.0.0
 *
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TbUser implements Serializable
{
    
    public TbUser()
    {
        // TODO Auto-generated constructor stub
    }
    
    private String userName;
    
    private String pwd;
    
    private String nickName;
    
    private String realName;
    
    private Integer gender;
    
    private String hobby;
    
    private String wearingHabit;
    
    private String email;
    
    private String mobile;
    
    private Date birthdate;
    
    private Date souvenirDate;
    
    private String color;
    
    private String skin;
    
    private String height;
    
    private String job;
    
    private Integer type;
    
    private String idNumber;
    
    private String headerImgUrl;
    
    private String sign;
    
    private Integer isEnabled;
    
    private Integer isLocked;
    
    private Date lockedTime;
    
    private Integer loginFailureCount;
    
    private Date lastLoginTime;
    
    private String lastLoginIp;
    
    private Integer memberRank;
    
    private Integer isBinding;
    
    private Integer lockPay;
    
    private Date lockPayTime;
    
    private Integer payPwdErrorTimes;
    
    private Date createTime;
    
    private String regIp;
    
    private Integer regSource;
    
    private String userCode;
    
    private String openid;
    
    private String token;
    
    private String qrcode;
    
    private Long accountId;
    
    private BigDecimal balance;
    
    private String kidney;
    
    private String zodiac;
    
    private Integer dsgnType;
    
    private Integer dsgnStatus;
    
    private String dsgnExpDesc;
    
    private String dsgnSketch;
    
    private Long gold;
    
    private Integer isDesigner;
    
    private Integer roleId;
    
    private String roleName;
    
    private String req1;
    
    private String req2;
    
    private String req3;
    
    private String req4;
    
    private String req5;
    
    private String req6;
    
    private String salt;
    
    private static final long serialVersionUID = 1L;
    
}