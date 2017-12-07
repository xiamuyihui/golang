package com.kingthy.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 14:37 on 2017/5/12.
 * @Modified by:
 */

@Data
@ToString
public class BuyersShowImg implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY, generator = "SELECT uuid_short()")
    private String uuid;

    private Date createDate;

    private Date modifyDate;

    private String creator;

    private String modifier;

    private String buyersUuid;

    private String imgUrl;

    private Boolean delFlag;
}
