package com.kingthy.service;

import com.kingthy.dto.IfoFullOrderDTO;
import com.kingthy.dto.IfoOrderDTO;
import com.kingthy.dto.IfoOrderItemDTO;
import com.kingthy.response.WebApiResponse;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 14:33 on 2017/9/25.
 * @Modified by:
 */
public interface IfoOrderInfoService
{

    /**
     * 创建订单
     * @param ifoOrderDTO
     * @return
     * @throws Exception
     */
    WebApiResponse<?> createIfoOrder(IfoOrderDTO ifoOrderDTO) throws Exception;

    WebApiResponse<?> createFullIfoOrder(IfoFullOrderDTO ifoFullOrderDTO) throws Exception;

    /**
     * 创建订单
     * @param ifoOrderDTO
     * @return
     * @throws Exception
     */
    WebApiResponse<?> createIfoOrder(IfoOrderItemDTO ifoOrderDTO) throws Exception;

    String getSn(String type);
}
