package com.kingthy.response;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 9:52 on 2017/10/11.
 * @Modified by:
 */
@Data
@ToString
public class NcpltInfoResp implements Serializable
{
    /**
     * 订单号
     */
    private String orderItemSn;

    /**
     * 款式编码
     */
    private String styleCode;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料用量
     */
    private String quantity;

    /**
     * 纸样
     */
    private String pltPath;

    /**
     * 裁床
     */
    private String ncPath;
}
