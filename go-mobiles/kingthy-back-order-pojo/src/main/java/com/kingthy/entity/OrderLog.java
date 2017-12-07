package com.kingthy.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Created by xumin on 2017/4/20.
 */

@Data
@ToString
public class OrderLog implements Serializable {
    private Long id;

    private Date createDate;

    private Date modifyDate;

    private Integer version;

    private String content;

    private String remark;

    private String operator;

    private Integer type;

    private String orderUuid;

    private String orderSn;

    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY, generator = "SELECT uuid_short()")
    private String uuid;

    private Boolean delFlag;

    private String creator;

    private String modifier;

    private static final long serialVersionUID = 1L;

}