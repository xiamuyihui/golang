package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.kingthy.cache.RedisManager;
import com.kingthy.common.KryoSerializeUtils;
import com.kingthy.dto.MemberFootmarkDTO;
import com.kingthy.dto.SimliarProductDTO;
import com.kingthy.dto.UpdateCacheQueueDTO;
import com.kingthy.dubbo.goods.service.GoodsDubboService;
import com.kingthy.dubbo.user.service.MemberFootmarkDubboService;
import com.kingthy.entity.Goods;
import com.kingthy.entity.MemberFootmark;
import com.kingthy.request.MemberFootmarkPageReq;
import com.kingthy.request.MemberFootmarkReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.MemberFootmarkService;
import com.kingthy.util.BeanMapperUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 *
 * 我的足迹 [业务处理实现类]
 * 
 * @author likejie 2017-4-21
 * 
 * @version 1.0.0
 *
 */
@Service("memberFootmarkService")
public class MemberFootmarkServiceImpl implements MemberFootmarkService
{

    @Reference(version = "1.0.0")
    private MemberFootmarkDubboService memberFootmarkDubboService;

    @Reference(version = "1.0.0")
    private GoodsDubboService goodsDubboService;

    @Autowired
    private RedisManager redisManager;

    @Override
    public List<MemberFootmarkDTO> getMemberFootmarkList(String memberUuid)
    {
        List<MemberFootmarkDTO> mfDtoList=new ArrayList<>();

        String cacheKey= redisManager.generateCacheKey(MemberFootmarkDTO.class,memberUuid);
        //从缓存读取
        String cacheData=redisManager.get(cacheKey);

        if(StringUtils.isNotBlank(cacheData)) {
            mfDtoList =  KryoSerializeUtils.deserializationList(cacheData,MemberFootmarkDTO.class);
        }else{
            //获取失效时间，如果已经失效，再查询数据库，即使为空直，如果没失效，也不会直接访问数据库
            long expire = redisManager.getExpire(cacheKey);
            if (expire <= 0) {
                //查询足迹
                List<MemberFootmark> list=memberFootmarkDubboService.getFootmarkByMemberUuid(memberUuid);
                List<String> productUuids=new ArrayList<>();
                for(MemberFootmark mf : list){
                    productUuids.add(mf.getProductUuid());
                }
                if(!productUuids.isEmpty()) {
                    //查询商品详情
                    List<Goods> goodsList = goodsDubboService.getGoodsListByIds(productUuids);
                    for (MemberFootmark mf : list) {
                        MemberFootmarkDTO dto = new MemberFootmarkDTO();
                        dto.setUuid(mf.getUuid());
                        dto.setCreateDate(mf.getCreateDate());
                        dto.setProductUuid(mf.getProductUuid());
                        for (Goods goods : goodsList) {
                            if (goods.getUuid().equals(mf.getProductUuid())) {
                                dto.setDesinger(goods.getDesinger());
                                dto.setGoodsDetails(goods.getGoodsDetails());
                                dto.setGoodsImage(goods.getGoodsImage());
                                dto.setGoodsName(goods.getGoodsName());
                                dto.setStandardPrice(goods.getStandardPrice());
                            }
                        }
                        mfDtoList.add(dto);
                    }
                }
                if (!mfDtoList.isEmpty()) {

                    cacheData=KryoSerializeUtils.serializationList(mfDtoList,MemberFootmarkDTO.class);
                }
                //加入缓存
                redisManager.setByValue(cacheKey, cacheData, redisManager.getRandomExpire(100), TimeUnit.HOURS);
            }
        }
        return mfDtoList;
    }


    @Override
    public PageInfo<MemberFootmarkDTO> pageGetMemberFootmarkList(MemberFootmarkPageReq req){


        MemberFootmark cond=new MemberFootmark();
        cond.setMemberUuid(req.getMemberUuid());
        PageInfo<MemberFootmark>  pageInfo= memberFootmarkDubboService.queryPage(req.getPageNum(),req.getPageSize(),cond);
        List<MemberFootmark> list=pageInfo.getList();

        List<String> productUuids=new ArrayList<>();
        for(MemberFootmark mf : list){
            productUuids.add(mf.getProductUuid());
        }
        List<MemberFootmarkDTO> mfDtoList=new ArrayList<>();
        if(!productUuids.isEmpty()) {
            //查询商品详情
            List<Goods> goodsList = goodsDubboService.getGoodsListByIds(productUuids);
            for (MemberFootmark mf : list) {
                MemberFootmarkDTO dto = new MemberFootmarkDTO();
                dto.setUuid(mf.getUuid());
                dto.setCreateDate(mf.getCreateDate());
                dto.setProductUuid(mf.getProductUuid());
                for (Goods goods : goodsList) {
                    if (goods.getUuid().equals(mf.getProductUuid())) {
                        dto.setDesinger(goods.getDesinger());
                        dto.setGoodsDetails(goods.getGoodsDetails());
                        dto.setGoodsImage(goods.getGoodsImage());
                        dto.setGoodsName(goods.getGoodsName());
                        dto.setStandardPrice(goods.getStandardPrice());
                    }
                }
                mfDtoList.add(dto);
            }
        }
        PageInfo<MemberFootmarkDTO> result=new PageInfo<>();
        result.setList(mfDtoList);
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotal(pageInfo.getTotal());
        return result;

    }


    @Override
    public WebApiResponse addMemberFootmark(MemberFootmarkReq req) {

        MemberFootmark memberFootmark = new MemberFootmark();
        BeanMapperUtil.copyProperties(req, memberFootmark);
        // 检查是否已添加该商品， 商品只能添加一次
        int count = memberFootmarkDubboService.selectCount(memberFootmark);
        if (count == 0) {
            Date nowDate = new Date();
            memberFootmark.setCreateDate(nowDate);
            memberFootmark.setModifyDate(nowDate);
            int res = memberFootmarkDubboService.insert(memberFootmark);
            if (res > 0) {
                //更新缓存
                String cacheKey = redisManager.generateCacheKey(MemberFootmarkDTO.class, "fs-"+memberFootmark.getMemberUuid());
                UpdateCacheQueueDTO updateCacheQueueDTO = new UpdateCacheQueueDTO();
                updateCacheQueueDTO.setCacheKey(cacheKey);
                redisManager.updateCache(updateCacheQueueDTO);

                return WebApiResponse.success();
            }else{
                return WebApiResponse.error(WebApiResponse.ResponseMsg.FAIL.getValue());
            }
        } else {
            return WebApiResponse.error("已添加到我的足迹");
        }

    }
    
    @Override
    public int batchDeleteFootmark(String memberUuid,List<String> uuids)
    {
        int res= memberFootmarkDubboService.batchDeleteFootmark(uuids);
        if(res>0) {
            //更新缓存
            String cacheKey = redisManager.generateCacheKey(MemberFootmarkDTO.class, memberUuid);
            UpdateCacheQueueDTO updateCacheQueueDTO = new UpdateCacheQueueDTO();
            updateCacheQueueDTO.setCacheKey(cacheKey);
            redisManager.updateCache(updateCacheQueueDTO);
        }
        return res;
    }
    
    @Override
    public List<SimliarProductDTO> getSimilarProduct(String productUuid)
    {
        return null;
    }
}
