package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 *
 * PublishCommentReq(描述其作用)
 * @author 赵生辉
 * @date  2017年05月19日 14:59
 *
 */
@Data
@ToString
public class PublishCommentReq implements Serializable
{
    /**
     * 评论内容
     */
    private String context;

    /**
     * 父类的业务主键
     */
    private String parentUuid;

    /**
     * 父类的业务主键
     */
    private String parentNick;

    /**
     * 动态的业务主键
     */
    private String momentUuid;

    /**
     * 会员头像
     */
    private String memberHead;

    /**
     * 会员昵称
     */
    private String memberNick;

    /**
     * 会员业务主键
     */
    private String token;

    private static final long serialVersionUID = 1L;

}
