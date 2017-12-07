package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingthy.dubbo.goods.service.GoodsDubboService;
import com.kingthy.dubbo.order.service.OrderItemDubboService;
import com.kingthy.dubbo.user.service.MemberFavoriteDubboService;
import com.kingthy.dubbo.user.service.MemberFittingDubboService;
import com.kingthy.entity.EsGoods;
import com.kingthy.entity.Goods;
import com.kingthy.entity.MemberFavorite;
import com.kingthy.repository.EsGoodsRepository;
import com.kingthy.service.EsGoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 11:36 on 2017/5/27.
 * @Modified by:
 */
@Service
public class EsGoodsServiceImpl implements EsGoodsService
{

    private static final Logger LOG = LoggerFactory.getLogger(EsGoodsServiceImpl.class);


    @Reference(version = "1.0.0", timeout = 3000)
    private GoodsDubboService goodsDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private MemberFavoriteDubboService memberFavoriteDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private MemberFittingDubboService memberFittingDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private OrderItemDubboService orderItemDubboService;

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
        Goods goods = goodsDubboService.selectOne(query);

        if (goods == null){

            repository.delete(goodsUuid);
            return;
        }

        //私人商品不同步到ES
        if (goods.getIsPrivate() != null && goods.getIsPrivate()){
            return;
        }

        //更新elasticsearch

        ExecutorService executor = null;

        Integer favoriteCount = 0;
        Long fittingCount = 0L;
        Long saleCount = 0L;

        try{

            executor = Executors.newFixedThreadPool(3);

            MemberFavorite memberFavorite = new MemberFavorite();
            memberFavorite.setFavoriteGoodsUuid(goodsUuid);
            memberFavorite.setDelFlag(false);

            Future<Integer> integerFuture = executor.submit(() -> memberFavoriteDubboService.selectCount(memberFavorite));
            Future<Long> futureFitting = executor.submit(() -> memberFittingDubboService.selectFittingCountByGoodsUuid(goodsUuid));
            Future<Long> futureSaleCount = executor.submit(() -> orderItemDubboService.selectSaleCountByGoodsUuid(goodsUuid));

            favoriteCount = integerFuture.get();

            fittingCount = futureFitting.get();

            saleCount = futureSaleCount.get();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOG.error(" task interrupted " + e );

        } catch (ExecutionException e) {

            LOG.error(" 线程池异常 " + e);

        }finally {
            if (executor != null){
                stop(executor);
            }
        }

        EsGoods esgoods = new EsGoods();

        Gson gson = new Gson();

        Type type = new TypeToken<ArrayList<EsGoods.GoodsImage>>(){}.getType();

        List<EsGoods.GoodsImage> goodsImages = gson.fromJson(goods.getGoodsImage(), type);

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
        esgoods.setFavoriteCount(Long.valueOf(favoriteCount == null ? 0 : favoriteCount));
        esgoods.setSaleCount(saleCount == null ? 0 : saleCount);
        esgoods.setFittingCount(fittingCount == null ? 0 : fittingCount);
        esgoods.setDesinger(goods.getDesinger());

        repository.save(esgoods);

    }


    /**
     * 关闭线程池
     * @param executor
     */
    static void stop(ExecutorService executor){

        try {

            executor.shutdown();
            executor.awaitTermination(3, TimeUnit.SECONDS);

        }catch (InterruptedException e){

            LOG.error("线程中断异常:" + e.getMessage());

            Thread.currentThread().interrupt();

        }finally {

            if (!executor.isTerminated()){
                LOG.error("杀死未完成的任务");
            }

            executor.shutdownNow();
        }

    }
}
