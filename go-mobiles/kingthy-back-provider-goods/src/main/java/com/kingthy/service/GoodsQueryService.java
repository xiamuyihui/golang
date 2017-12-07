package com.kingthy.service;

import com.kingthy.dto.GoodsDTO;

import java.util.List;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 16:43 on 2017/11/16.
 * @Modified by:
 */
public interface GoodsQueryService
{
    Long saleCountByGoodsUuidAndMonth(String goodsUuid);

    Long selectFavoriteCountByGoodsUuidAndMembersUuid(String goodsUuid, String memberUuid);

    Long selectBuyerShowCountByGoodUuid(String goodsUuid);

    GoodsDTO.DesignerInfo selectDesignerInfoByDesignerUuid(String memberUuid, String designer);

    List<GoodsDTO.FigureInfo> parseFigureInfo(String memberUuid);

    List<GoodsDTO.BuyersShowInfo> parseBuyersShow(String goodsUuid);
}
