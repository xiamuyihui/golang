package com.kingthy.service;


import com.kingthy.dto.OrderCommentImgDTO;

/**
 * @AUTHORS xumin
 * @Description:
 * @DATE Created by 14:32 on 2017/5/12.
 * @Modified by:
 */

public interface BuyersShowImgService {
    void saveBuyersShowImg(OrderCommentImgDTO imgDTO) throws Exception;

    /**
     * 敏感数据过滤
     * @param imgDTO
     * @throws Exception
     */
    void sensitiveDataFilter(OrderCommentImgDTO imgDTO) throws Exception;

    void testElasticsearch(String goodsUuid) throws Exception;
}
