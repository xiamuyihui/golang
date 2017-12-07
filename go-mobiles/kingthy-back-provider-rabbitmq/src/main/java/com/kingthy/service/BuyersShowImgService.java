package com.kingthy.service;


import com.kingthy.dto.OrderCommentImgDTO;
import com.kingthy.entity.BuyersShow;
import com.kingthy.entity.Goods;

import java.util.List;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 14:32 on 2017/5/12.
 * @Modified by:
 */

public interface BuyersShowImgService {

    /**
     * 保存买家秀图片
     * @param imgDTO
     * @throws Exception
     */
    void saveBuyersShowImg(OrderCommentImgDTO imgDTO) throws Exception;

    /**
     * 敏感数据过滤
     * @param imgDTO
     * @throws Exception
     */
    void sensitiveDataFilter(OrderCommentImgDTO imgDTO) throws Exception;

    /**
     * 测试es
     * @param goodsUuid
     * @throws Exception
     */
    void testElasticsearch(String goodsUuid) throws Exception;

    /**
     * 根据商品名称删除es商品数据
     * @param goodsName
     */
    void delElasticSearch(String goodsName);

    /**
     * 测试insert
     * @param buyersShow
     * @return
     */
    String testinsertReturnUuid(BuyersShow buyersShow);

    /**
     * 查询买家秀
     * @param buyersShow
     * @return
     */
    List<BuyersShow> queryPage(BuyersShow buyersShow);

    /**
     * 查询商品
     * @return
     */
    List<Goods> queryGoods();
}
