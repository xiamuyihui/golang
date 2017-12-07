package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 11:03 on 2017/5/19.
 * @Modified by:
 */
@Data
@ToString
public class GoodsClickReq implements Serializable {

    private String goodsUuid;

    private Integer clickCount;
}
