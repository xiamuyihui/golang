package com.kingthy.response;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Created by Administrator on 2017/4/26.
 */
@Data
@ToString
public class MemberFavoriteResp implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String uuid;

    private String goodsName;

    private String cover;

    private String favoriteUuid;

    private String goodsStatus;

    private String price;
}
