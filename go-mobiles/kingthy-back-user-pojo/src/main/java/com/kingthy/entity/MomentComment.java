package com.kingthy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * MomentComment
 * @author none
 * @date  2017/6/8.
 */
@Data
@ToString
public class MomentComment implements Serializable {
    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date modifyDate;

    private Integer version;

    private String creator;

    private String modeifier;

    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid_short()")
    private String uuid;

    private Boolean delFlag;

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
    private String memberUuid;

    /**
     * 点赞数
     */
    private Long likeAmount;

    private static final long serialVersionUID = 1L;

}