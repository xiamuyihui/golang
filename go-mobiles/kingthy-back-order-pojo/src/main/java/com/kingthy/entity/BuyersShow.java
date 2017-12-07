package com.kingthy.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 买家秀
 * @author xumin
 * @Description:
 * @DATE Created by 17:23 on 2017/5/11.
 * @Modified by:
 */

@Data
@ToString
public class BuyersShow implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Date createDate;

    private Date modifyDate;

    private String creator;

    private String modifier;

    private String memberName;

    private String memberUuid;

    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY, generator = "SELECT uuid_short()")
    private String uuid;

    private Boolean delFlag;
    private Boolean anonymousFlag;
    private Boolean status;

    private String orderSn;
    private String orderUuid;
    private String goodsUuid;
    private String content;
}
