package com.kingthy.dto;

import com.kingthy.entity.Moments;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * MomentDto
 * @author 赵生辉
 * @date  2017/5/22.
 */
@Data
@ToString
public class MomentDto extends Moments implements Serializable
{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "是否点赞")
    private String isLike;

    @ApiModelProperty(name = "是否关注")
    private String isAttention;

    @ApiModelProperty(name = "是否可以删除")
    private String isDel;
}
