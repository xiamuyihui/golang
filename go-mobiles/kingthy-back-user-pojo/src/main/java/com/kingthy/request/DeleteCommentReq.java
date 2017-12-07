package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;



/**
 *
 * DeleteCommentReq
 * @author 赵生辉
 * @date  2017/9/11.
 *
 */
@Data
@ToString
public class DeleteCommentReq implements Serializable
{
    /**
     * 评论业务主键
     */
    private String uuid;


    /**
     * 会员token
     */
    private String token;

    /**
     * 动态的业务主键
     */
    private String momentUuid;

    private static final long serialVersionUID = 1L;
}
