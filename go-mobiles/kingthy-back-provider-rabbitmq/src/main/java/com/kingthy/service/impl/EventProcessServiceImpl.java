package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.dubbo.cart.service.EventProcessDubboService;
import com.kingthy.request.EventPublishReq;
import com.kingthy.service.EventProcessService;
import org.springframework.stereotype.Service;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 11:44 on 2017/11/28.
 * @Modified by:
 */
@Service
public class EventProcessServiceImpl implements EventProcessService
{

    @Reference(version = "1.0.0")
    private EventProcessDubboService eventProcessDubboService;

    @Override
    public int insert(EventPublishReq message)
    {
        return eventProcessDubboService.insert(message);
    }
}
