package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.annotation.QueryCache;
import com.kingthy.annotation.QueryCacheKey;
import com.kingthy.common.CacheNameSpace;
import com.kingthy.dto.BuyersShowDTO;
import com.kingthy.dto.CommentMemberDto;
import com.kingthy.dto.GoodsDTO;
import com.kingthy.dubbo.opus.service.OpusDubboService;
import com.kingthy.dubbo.order.service.OrderItemDubboService;
import com.kingthy.dubbo.review.service.BuyersShowDubboService;
import com.kingthy.dubbo.user.service.FigureDubboService;
import com.kingthy.dubbo.user.service.MemberDubboService;
import com.kingthy.dubbo.user.service.MemberFavoriteDubboService;
import com.kingthy.entity.Figure;
import com.kingthy.service.GoodsQueryService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 16:44 on 2017/11/16.
 * @Modified by:
 */
@Service
public class GoodsQueryServiceImpl implements GoodsQueryService
{

    @Reference(version = "1.0.0")
    private OrderItemDubboService orderItemDubboService;

    @Reference(version = "1.0.0")
    private MemberFavoriteDubboService memberFavoriteDubboService;

    @Reference(version = "1.0.0")
    private BuyersShowDubboService buyersShowDubboService;

    @Reference(version = "1.0.0")
    private MemberDubboService memberDubboService;

    @Reference(version = "1.0.0")
    private OpusDubboService opusDubboService;

    @Reference(version = "1.0.0")
    private FigureDubboService figureDubboService;

    @Override
//    @QueryCache(value = CacheNameSpace.GOODS_SALE_COUNT)
    public Long saleCountByGoodsUuidAndMonth(@QueryCacheKey String goodsUuid){
        return orderItemDubboService.selectSaleCountByGoodsUuidAndMonth(goodsUuid);
    }

//    @QueryCache(value = CacheNameSpace.GOODS_FAVORITE)
    @Override
    public Long selectFavoriteCountByGoodsUuidAndMembersUuid(@QueryCacheKey String goodsUuid, @QueryCacheKey String memberUuid) {
        return memberFavoriteDubboService.selectFavoriteCountByGoodsUuidAndMembersUuid(goodsUuid, memberUuid);
    }

//    @QueryCache(value = CacheNameSpace.GOODS_BUYER_SHOW_COUNT)
    @Override
    public Long selectBuyerShowCountByGoodUuid(@QueryCacheKey String goodsUuid) {
        return buyersShowDubboService.selectBuyerShowCountByGoodUuid(goodsUuid);
    }

//    @QueryCache(value = CacheNameSpace.GOODS_DESIGNER_INFO)
    @Override
    public GoodsDTO.DesignerInfo selectDesignerInfoByDesignerUuid(@QueryCacheKey String memberUuid, @QueryCacheKey String designer) {
        GoodsDTO.DesignerInfo designerInfo =
                memberDubboService.selectDesignerInfoByDesignerUuid(memberUuid, designer);
        if (designerInfo != null)
        {
            long count = opusDubboService.findNumByDesignerUuid(designerInfo.getDesignerUuid());
            designerInfo.setNumber(count);
            String icon = designerInfo.getIcon();
            designerInfo.setIcon(icon == null ? "" : designerInfo.getIcon());
        }
        return designerInfo;
    }

//    @QueryCache(value = CacheNameSpace.MEMBER_FIGURE)
    @Override
    public List<GoodsDTO.FigureInfo> parseFigureInfo(@QueryCacheKey String memberUuid)
    {

        Figure var1 = new Figure();
        var1.setMemberUuid(memberUuid);
        var1.setDelFlag(false);

        return figureDubboService.select(var1)
                .stream()
                .map(figure -> new GoodsDTO.FigureInfo(figure.getFigureName(), figure.getUuid(), BigDecimal.valueOf(0.0)))
                .collect(Collectors.toList());

    }

//    @QueryCache(value = CacheNameSpace.GOODS_BUYERS_SHOW_LIST)
    @Override
    public List<GoodsDTO.BuyersShowInfo> parseBuyersShow(@QueryCacheKey String goodsUuid)
    {

        List<GoodsDTO.BuyersShowInfo> buyersShowInfoList = new ArrayList<>();

        //查询2条买家秀记录
        List<BuyersShowDTO> showDTOList = buyersShowDubboService.selectBuyersShowByGoodsUuid(goodsUuid);

        if (showDTOList != null && !showDTOList.isEmpty())
        {

            ArrayList<BuyersShowDTO> seriaList = (ArrayList<BuyersShowDTO>)showDTOList;

            Map<String, CommentMemberDto> map = new HashMap<>();

            //查询会员
            memberDubboService.findMemberInfoByList(seriaList).forEach(dto -> map.put(dto.getMemberUuid(), dto));

            showDTOList.forEach(dto -> {

                GoodsDTO.BuyersShowInfo buyersShowInfo = new GoodsDTO.BuyersShowInfo();

                buyersShowInfo.setContent(dto.getContent());
                buyersShowInfo
                        .setImg(dto.getImages() == null ? new ArrayList<>() : Arrays.asList(dto.getImages().split(",")));

                buyersShowInfo
                        .setMemberIcon(map.containsKey(dto.getMemberUuid()) ? map.get(dto.getMemberUuid()).getIcon() : "");

                buyersShowInfo.setMemberName(dto.getMemberName());

                buyersShowInfoList.add(buyersShowInfo);

            });
        }

        return buyersShowInfoList;
    }
}
