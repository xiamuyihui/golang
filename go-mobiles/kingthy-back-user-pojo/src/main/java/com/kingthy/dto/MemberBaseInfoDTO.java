package com.kingthy.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name MemberBaseInfoDTO
 * @description 会员基本信息封装类
 * @create 2017/11/6
 */
@Data
@ToString
public class MemberBaseInfoDTO implements Serializable{

    @ApiModelProperty("业务主键")
    private String uuid;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("用户头像")
    private String headImage;
}
