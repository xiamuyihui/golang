package com.kingthy.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * (描述其作用)
 *
 * @author 赵生辉 2017/7/17 15:04
 *
 * @version 1.0.0
 *
 */
@Data
@ToString
public class DeliveryCorp implements Serializable {

    private Long id;

    private Date createDate;

    private Date modifyDate;

    private Long version;

    private Integer orders;

    private String code;

    private String name;

    private String url;

    private static final long serialVersionUID = 1L;
}