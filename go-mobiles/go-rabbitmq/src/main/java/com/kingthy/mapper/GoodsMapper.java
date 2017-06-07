package com.kingthy.mapper;

import com.kingthy.dto.BuyersShowDTO;
import com.kingthy.entity.Goods;
import com.kingthy.util.MyMapper;

import java.util.List;

public interface GoodsMapper extends MyMapper<Goods>
{
    Long selectFavoriteCountByGoodsUuid(String goodsUuid);

    Long selectFittingCountByGoodsUuid(String goodsUuid);

    Long selectSaleCountByGoodsUuid(String goodsUuid);

}
