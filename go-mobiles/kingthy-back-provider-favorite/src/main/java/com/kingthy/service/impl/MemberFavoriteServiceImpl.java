package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.kingthy.cache.RedisManager;
import com.kingthy.common.KryoSerializeUtils;
import com.kingthy.dto.UpdateCacheQueueDTO;
import com.kingthy.dubbo.goods.service.GoodsDubboService;
import com.kingthy.dubbo.user.service.MemberFavoriteDubboService;
import com.kingthy.entity.Goods;
import com.kingthy.entity.MemberFavorite;
import com.kingthy.request.MemberFavoritePageReq;
import com.kingthy.request.MemberFavoriteReq;
import com.kingthy.response.MemberFavoriteResp;
import com.kingthy.service.MemberFavoriteService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author :pany
 * @date:2016-11-3
 *
 */
@Service(value = "memberFavoriteService")
public class MemberFavoriteServiceImpl implements MemberFavoriteService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberFavoriteServiceImpl.class);

    @Reference(version = "1.0.0")
    private MemberFavoriteDubboService memberFavoriteDubboService;

    @Reference(version = "1.0.0")
    private GoodsDubboService goodsDubboService;

    @Autowired
    private RedisManager redisManager;

    @Override
    public List<MemberFavoriteResp> getMemberFavorites(String memberUudi)
    {
        List<MemberFavoriteResp> resultList=new ArrayList<>();
        String cacheKey= redisManager.generateCacheKey(MemberFavoriteResp.class,memberUudi);
        //从缓存读取
        String cacheData=redisManager.get(cacheKey);

        if(StringUtils.isNotBlank(cacheData)) {
            resultList = KryoSerializeUtils.deserializationList(cacheData, MemberFavoriteResp.class);
        }else{
            long expire = redisManager.getExpire(cacheKey);
            if (expire <= 0) {
                MemberFavorite cond=new MemberFavorite();
                cond.setFavoriteMembersUuid(memberUudi);
                cond.setDelFlag(false);
                List<MemberFavorite> memberFavorites=memberFavoriteDubboService.select(cond);
                List<String> productUuids= new ArrayList<>() ;
                for(MemberFavorite mf:memberFavorites){
                    productUuids.add(mf.getFavoriteGoodsUuid());
                }
                if(!productUuids.isEmpty()) {
                    //查询商品详情
                    List<Goods> goodsList = goodsDubboService.getGoodsListByIds(productUuids);
                    for (MemberFavorite mf : memberFavorites) {
                        MemberFavoriteResp memberFavoriteResp = new MemberFavoriteResp();
                        memberFavoriteResp.setFavoriteUuid(mf.getUuid());
                        memberFavoriteResp.setUuid(mf.getFavoriteGoodsUuid());
                        for (Goods p : goodsList) {
                            if (mf.getFavoriteGoodsUuid().equals(p.getUuid())) {
                                memberFavoriteResp.setGoodsName(p.getGoodsName());
                                String goodsStatus = "";
                                if ("0".equals(p.getStatus())) {
                                    goodsStatus = "已下架";
                                }
                                if ("1".equals(p.getStatus())) {
                                    goodsStatus = "已上架";
                                }
                                memberFavoriteResp.setGoodsStatus(goodsStatus);
                                memberFavoriteResp.setPrice(p.getStandardPrice().toString());
                                memberFavoriteResp.setCover(p.getCover());
                                break;
                            }
                        }
                        resultList.add(memberFavoriteResp);
                    }
                }
                if (!resultList.isEmpty()) {
                    cacheData=KryoSerializeUtils.serializationList(resultList,MemberFavoriteResp.class);
                }
                //加入缓存
                redisManager.set(cacheKey,cacheData , redisManager.getRandomExpire(), TimeUnit.DAYS);
            }

        }
        return resultList;
    }
    private PageInfo<MemberFavoriteResp> pageGetFavoriteList(MemberFavoritePageReq req){

        //分页获取收藏数据
        MemberFavorite cond=new MemberFavorite();
        cond.setFavoriteMembersUuid(req.getMemberUuid());
        cond.setDelFlag(false);
        PageInfo<MemberFavorite> pageInfo = memberFavoriteDubboService.queryPage(req.getPageNum(),req.getPageSize(),cond);
        List<MemberFavorite> mfList = pageInfo.getList();
        List<String> productUuids = new ArrayList<>();
        for (MemberFavorite mf : mfList) {
            productUuids.add(mf.getFavoriteGoodsUuid());
        }
        List<MemberFavoriteResp> resultList=new ArrayList<>();
        if (!productUuids.isEmpty()) {
            //查询商品详情
            List<Goods> goodsList = goodsDubboService.getGoodsListByIds(productUuids);
            //组装数据到新集合：resultList
            for (MemberFavorite mf : mfList) {
                MemberFavoriteResp memberFavoriteResp = new MemberFavoriteResp();
                memberFavoriteResp.setFavoriteUuid(mf.getUuid());
                memberFavoriteResp.setUuid(mf.getFavoriteGoodsUuid());
                for (Goods p : goodsList) {
                    if (mf.getFavoriteGoodsUuid().equals(p.getUuid())) {
                        memberFavoriteResp.setGoodsName(p.getGoodsName());
                        String goodsStatus = "";
                        if ("0".equals(p.getStatus())) {
                            goodsStatus = "已下架";
                        }
                        if ("1".equals(p.getStatus())) {
                            goodsStatus = "已上架";
                        }
                        memberFavoriteResp.setGoodsStatus(goodsStatus);
                        memberFavoriteResp.setPrice(p.getStandardPrice().toString());
                        memberFavoriteResp.setCover(p.getCover());
                        break;
                    }
                }
                resultList.add(memberFavoriteResp);
            }
        }
        PageInfo<MemberFavoriteResp> pageResult=new PageInfo<>();
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setList(resultList);
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setTotal(pageInfo.getTotal());
        return pageResult;
    }
    /**
     *
     *分页获取会员收藏数据
     * @param req
     * @return
     */
    @Override
    public PageInfo<MemberFavoriteResp> pageGetMemberFavoriteList(MemberFavoritePageReq req){

        PageInfo<MemberFavoriteResp> pageResult=null;
        if(req.getPageNum()==null||req.getPageNum()<=1) {
            //缓存第一屏
            String cacheKey = redisManager.generateCacheKey(MemberFavoriteResp.class, "first-page-" + req.getMemberUuid());
            //从缓存读取
            String cacheData = redisManager.get(cacheKey);
            if (StringUtils.isNotBlank(cacheData)) {
                pageResult=JSON.parseObject(cacheData,new TypeReference<PageInfo<MemberFavoriteResp>>(){}.getType());
            } else {
                long expire = redisManager.getExpire(cacheKey);
                if (expire <= 0) {
                    pageResult=pageGetFavoriteList(req);
                    if (pageResult!=null) {
                        cacheData=JSON.toJSONString(pageResult);
                    }
                    //加入缓存
                    redisManager.set(cacheKey,cacheData , redisManager.getRandomExpire(), TimeUnit.DAYS);
                }
            }
        }else {
            pageResult = pageGetFavoriteList(req);
        }
        return pageResult;
    }

    @Override
    public int addMemberFavorite(MemberFavorite memberFavorite)
    {
        Date now=new Date();
        memberFavorite.setCreateDate(now);
        memberFavorite.setModifyDate(now);
        memberFavorite.setDelFlag(false);
        int res= memberFavoriteDubboService.insert(memberFavorite);
        if(res>0) {
            //更新缓存
            String cacheKey = redisManager.generateCacheKey(MemberFavoriteResp.class, "first-page-" + memberFavorite.getFavoriteMembersUuid());
            UpdateCacheQueueDTO updateCacheQueueDTO = new UpdateCacheQueueDTO();
            updateCacheQueueDTO.setCacheKey(cacheKey);
            redisManager.updateCache(updateCacheQueueDTO);
        }
        return res;


    }

    @Override
    public int delMemberFacorite(MemberFavoriteReq memberFavoriteReq)
    {
        LOGGER.info(memberFavoriteReq.toString());
        int result = 0;
        memberFavoriteReq.setModifyDate(new Date());
        //清除该用户下的所有收藏
        if (memberFavoriteReq.isCleanAll())
        {
            result = memberFavoriteDubboService.updateAllFavorite(memberFavoriteReq);
        }
        //清除单个或多个收藏
        else
        {
            result = memberFavoriteDubboService.updateSomeFavorite(memberFavoriteReq);
        }
        LOGGER.info("result : " + result);
        if(result>0) {
            //更新缓存
            String cacheKey = redisManager.generateCacheKey(MemberFavoriteResp.class, "first-page-" + memberFavoriteReq.getMemberUuid());
            UpdateCacheQueueDTO updateCacheQueueDTO = new UpdateCacheQueueDTO();
            updateCacheQueueDTO.setCacheKey(cacheKey);
            redisManager.updateCache(updateCacheQueueDTO);
        }
        return result;
    }

    @Override
    public boolean isFacorited(MemberFavorite memberFavorite)
    {
        memberFavorite.setDelFlag(false);
        List<MemberFavorite> memberFavorites = memberFavoriteDubboService.select(memberFavorite);
        return memberFavorites != null && !memberFavorites.isEmpty();
    }

}
