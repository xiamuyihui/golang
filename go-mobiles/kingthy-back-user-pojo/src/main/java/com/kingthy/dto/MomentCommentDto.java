package com.kingthy.dto;

import com.kingthy.entity.MomentComment;
import lombok.Data;
import lombok.ToString;

import java.util.List;


/**
 *
 * 评论
 * @author 赵生辉
 * @date  2017/5/22.
 */
@Data
@ToString
public class MomentCommentDto extends MomentComment
{
    private String isLike;

    private String isDel;

}
