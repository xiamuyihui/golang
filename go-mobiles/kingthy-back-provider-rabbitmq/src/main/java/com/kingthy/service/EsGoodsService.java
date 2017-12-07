package com.kingthy.service;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 11:35 on 2017/5/27.
 * @Modified by:
 */
public interface EsGoodsService {

    /**
     * 同步商品到ES
     * @param goodsUuid
     * @throws Exception
     */
    void syncEsGoods(String goodsUuid) throws Exception;
}
