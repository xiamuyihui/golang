package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * CreataShopReq(描述其作用)
 * <p>
 * @author 赵生辉 2017年07月21日 17:12
 *
 * @version 1.0.0
 */
@Data
@ToString
public class CreataShopReq implements Serializable
{
    private String name;

    private String address;

    private String city;

    private String telephone;

    private String openTime;

    private String closeTime;

    private Boolean status;

    private String longitude;

    private String latitude;

    private String image;

    private String description;

    private static final long serialVersionUID = 1L;
}
