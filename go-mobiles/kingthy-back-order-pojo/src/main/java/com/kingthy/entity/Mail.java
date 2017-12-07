package com.kingthy.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Mail(描述其作用)
 * <p>
 * @author 赵生辉 2017年06月07日 9:26
 *
 * @version 1.0.0
 */
@Data
@ToString
public class Mail implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String smtp;

    private String from;

    private String to;

    private String copyto;

    private String subject;

    private String content;

    private String username;

    private String password;

    private String filename;
}
