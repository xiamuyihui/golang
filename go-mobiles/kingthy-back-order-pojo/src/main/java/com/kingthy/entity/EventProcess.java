package com.kingthy.entity;

import com.kingthy.common.BaseTableFileds;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 11:32 on 2017/8/23.
 * @Modified by:
 */
@Data
@ToString
public class EventProcess extends BaseTableFileds implements Serializable
{

    private String eventStatus;

    private String eventType;

    private String payload;

    private String sharding;

    private static final long serialVersionUID = 1L;

    /**
     * 事件状态
     */
    public enum EventStatusEnum
    {

        //待发布(NEW)  已发布(PUBLISHED)待发布(NEW)  已发布(PUBLISHED)
        NEW,PUBLISHED
    }

    public enum EventTypeEnum
    {
        //更新购物车(CART)
        CART
    }

}

