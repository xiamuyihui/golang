package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.annotation.QueryCache;
import com.kingthy.annotation.QueryCacheKey;
import com.kingthy.common.CacheNameSpace;
import com.kingthy.dubbo.goods.service.GoodsDubboService;
import com.kingthy.entity.Goods;
import com.kingthy.service.GoodsService;
import org.springframework.stereotype.Service;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 17:05 on 2017/11/20.
 * @Modified by:
 */
@Service
public class GoodsServiceImpl implements GoodsService
{

    @Reference(version = "1.0.0", timeout = 3000)
    private GoodsDubboService goodsDubboService;


    @QueryCache(value = CacheNameSpace.GOODS)
    @Override
    public Goods selectGoodsByUuid(@QueryCacheKey String goodsUuid) throws Exception {
        return goodsDubboService.selectByUuid(goodsUuid);
    }
}
