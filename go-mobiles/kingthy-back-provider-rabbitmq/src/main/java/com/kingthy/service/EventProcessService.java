package com.kingthy.service;

import com.kingthy.request.EventPublishReq;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 11:44 on 2017/11/28.
 * @Modified by:
 */
public interface EventProcessService
{
    int insert(EventPublishReq message);
}
