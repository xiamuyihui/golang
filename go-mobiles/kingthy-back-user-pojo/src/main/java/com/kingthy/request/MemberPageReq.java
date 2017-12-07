package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * MemberPageReq
 * @author 陈钊
 * @date  2017/4/14.
 *
 */
@Data
@ToString
public class MemberPageReq implements Serializable{
    /**
     * 手机号
     */
    private String phone;
    /**
     * 用户名
     */
    private String userName;

    private Date startTime;

    private Date endTime;

    private Integer pageNum;

    private Integer pageSize;
}
