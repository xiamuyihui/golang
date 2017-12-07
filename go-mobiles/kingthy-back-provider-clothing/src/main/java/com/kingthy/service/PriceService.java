package com.kingthy.service;

import com.kingthy.request.CalculatePriceReq;
import com.kingthy.response.WebApiResponse;

/**
 * 描述：渲染业务逻辑处理接口
 * @author  likejie
 * @date 2017/10/10.
 */
public interface PriceService {

    /**
     * 获取价格
     * @param req
     * @return
     **/
    WebApiResponse<?> getPrice(CalculatePriceReq req);

    /**
     * 获取价格
     * @param req
     * @return
     **/
    WebApiResponse<?> getPrice1(CalculatePriceReq req);
}
