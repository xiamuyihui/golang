package com.kingthy.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name CommentMemberDto
 * @description 用户出参封装类
 * @create 2017/8/11
 */
@Data
@ToString
public class CommentMemberDto implements Serializable{

    @ApiModelProperty("评论人")
    private String memberName;

    /**
     * 用户uuid
     */
    private String memberUuid;

    @ApiModelProperty("评论人头像")
    private String icon;
}
