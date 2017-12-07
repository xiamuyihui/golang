package com.kingthy.dto;

import com.kingthy.entity.BuyersShowImg;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author Created by likejie on 2017/8/24.
 */
@Data
@ToString
public class BuyersShowReviewDTO implements Serializable
{
    private String uuid;

    private String orderSn;

    private String orderUuid;

    private String goodsUuid;

    private String memberName;

    private String memberUuid;

    private Boolean anonymousFlag;

    private String ip;

    private String content;

    private Integer version;

    private Boolean status;

    private String goodsName;

    private List<BuyersShowImg> buyersShowImgList;

    private String replyContent;
}
