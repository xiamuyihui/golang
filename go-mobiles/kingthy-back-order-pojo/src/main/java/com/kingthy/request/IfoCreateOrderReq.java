package com.kingthy.request;

import com.kingthy.common.BaseReq;
import com.kingthy.entity.IfoOrderInfo;
import com.kingthy.entity.IfoOrderInfoDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 14:37 on 2017/9/25.
 * @Modified by:
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class IfoCreateOrderReq extends BaseReq implements Serializable
{
    /**
     * 主订单信息
     */
    private IfoOrderInfo ifoOrderInfo;

    /**
     * 子订单数组
     */
    private List<IfoOrderInfoDetail> detailList;
}
