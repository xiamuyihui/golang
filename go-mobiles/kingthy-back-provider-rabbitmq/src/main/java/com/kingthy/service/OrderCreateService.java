package com.kingthy.service;

import com.kingthy.dto.OrderCreateMqDTO;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 15:45 on 2017/11/14.
 * @Modified by:
 */
public interface OrderCreateService {

    void create(OrderCreateMqDTO message) throws Exception;
}
