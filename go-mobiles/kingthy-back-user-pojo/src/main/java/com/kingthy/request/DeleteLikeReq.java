package com.kingthy.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 *
 * DeleteLikeReq
 * @author 赵生辉
 * @date  2017/9/11.
 *
 */
@Data
@ToString
public class DeleteLikeReq implements Serializable
{
    private String momentUuid;

    @ApiModelProperty("用户token")
    private String token;


    private Integer type;

    private static final long serialVersionUID = 1L;
}
