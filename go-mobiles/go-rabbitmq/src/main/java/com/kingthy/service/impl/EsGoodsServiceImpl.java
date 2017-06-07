package com.kingthy.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingthy.entity.EsGoods;
import com.kingthy.entity.Goods;
import com.kingthy.mapper.GoodsMapper;
import com.kingthy.repository.EsGoodsRepository;
import com.kingthy.service.EsGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @AUTHORS xumin
 * @Description:
 * @DATE Created by 11:36 on 2017/5/27.
 * @Modified by:
 */
@Service
public class EsGoodsServiceImpl implements EsGoodsService
{

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private EsGoodsRepository repository;

    /**
     * 同步es 商品信息
     * @param goodsUuid
     * @throws Exception
     */
    @Override
    public void syncEsGoods(String goodsUuid) throws Exception {

        if (StringUtils.isEmpty(goodsUuid)){
            return;
        }

        Goods query = new Goods();
        query.setUuid(goodsUuid);
        query.setStatus(Goods.GoodsStatus.goodsUp.getValue());
        Goods goods = goodsMapper.selectOne(query);

        if (goods == null){
            repository.delete(goodsUuid);
            return;
        }

        //更新elasticsearch
        Long favoriteCount = goodsMapper.selectFavoriteCountByGoodsUuid(goodsUuid);

        Long fittingCount = goodsMapper.selectFittingCountByGoodsUuid(goodsUuid);

        Long saleCount = goodsMapper.selectSaleCountByGoodsUuid(goodsUuid);

        EsGoods esgoods = new EsGoods();

        Gson gson = new Gson();

        Type type = new TypeToken<ArrayList<EsGoods.GoodsImage>>(){}.getType();

        List<EsGoods.GoodsImage> goodsImages  = gson.fromJson(goods.getGoodsImage(), type);

        List<EsGoods.MaterielInfo> materielInfos = gson.fromJson(goods.getMaterielInfo(), new TypeToken<ArrayList<EsGoods.MaterielInfo>>(){}.getType());

        esgoods.setUuid(goods.getUuid());
        esgoods.setCreateDate(goods.getCreateDate().getTime() + "");
        esgoods.setGoodsName(goods.getGoodsName());
        esgoods.setGoodsFeature(goods.getGoodsFeature());
        esgoods.setClicks(goods.getClicks());
        esgoods.setCover(goods.getCover());

/*        String [] ids = {"97071061572518322","97071061572518323","97100764777808453", "97100764777809157"};

        Random random = new Random();
        esgoods.setGoodsCategoryUuid(ids[random.nextInt(3)]);*/

        esgoods.setGoodsCategoryUuid(goods.getGoodsCategoryUuid());
        esgoods.setGoodsImage(goodsImages);
        esgoods.setGoodsStyleUuid(goods.getGoodsStyleUuid());
        esgoods.setStandardPrice(goods.getStandardPrice());
        esgoods.setMaterielInfo(materielInfos);
        esgoods.setFitAgeOld(goods.getFitAgeOld() == null ? 0 : goods.getFitAgeOld());
        esgoods.setFitAgeYoung(goods.getFitAgeYoung() == null ? 0 : goods.getFitAgeYoung());
        esgoods.setFavoriteCount(favoriteCount == null ? 0 : favoriteCount);
        esgoods.setSaleCount(saleCount == null ? 0 : saleCount);
        esgoods.setFittingCount(fittingCount == null ? 0 : fittingCount);
        repository.save(esgoods);

    }
}
