package com.kingthy.service;

import com.kingthy.dto.IfoOrderItemDTO;
import com.kingthy.response.WebApiResponse;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 19:14 on 2017/9/25.
 * @Modified by:
 */
public interface IfoOrderInfoDetailService
{
    /**
     * 创建子订单
     * @param ifoOrderItemDTO
     * @return
     * @throws Exception
     */
    WebApiResponse<?> createOrderItem(IfoOrderItemDTO ifoOrderItemDTO) throws Exception;
}
