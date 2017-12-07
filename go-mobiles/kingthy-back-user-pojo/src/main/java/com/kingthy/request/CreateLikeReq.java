package com.kingthy.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * CreateLikeReq
 * @author 赵生辉
 * @date  2017/9/11.
 *
 */
@Data
@ToString
public class CreateLikeReq implements Serializable
{
    @ApiModelProperty("点赞的内容uuid（如动态uuid，评论uuid，买家秀uuid）")
    private String momentUuid;

    @ApiModelProperty("用户token")
    private String token;

    @ApiModelProperty("类型(0:动态 1:评论,2:买家秀)")
    private Integer type;

    private static final long serialVersionUID = 1L;
}
