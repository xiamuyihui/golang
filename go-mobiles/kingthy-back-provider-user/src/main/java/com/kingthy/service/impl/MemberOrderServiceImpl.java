/**
 * 系统项目名称
 * com.kingthy.service.impl
 * MemberOrderServiceImpl.java
 * 
 * 2017年4月24日-上午9:44:28
 *  2017金昔科技有限公司-版权所有
 *
 */
package com.kingthy.service.impl;



import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.kingthy.cache.RedisManager;
import com.kingthy.common.KryoSerializeUtils;
import com.kingthy.dto.MemberOrderDTO;
import com.kingthy.dubbo.goods.service.GoodsDubboService;
import com.kingthy.dubbo.order.service.OrderItemDubboService;
import com.kingthy.entity.Goods;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kingthy.request.MemberOrderReq;
import com.kingthy.service.MemberOrderService;

/**
 * @author 李克杰 2017年4月24日 上午9:44:28
 * @version 1.0.0
 */
@Service("memberOrderService")
public class MemberOrderServiceImpl implements MemberOrderService
{

    @Reference(version = "1.0.0")
    private GoodsDubboService goodsDubboService;

    @Reference(version = "1.0.0")
    private OrderItemDubboService orderItemDubboService;

    @Autowired
    private RedisManager redisManager;

    @Override
    public List<MemberOrderDTO> getMemberOrderList(String memberUuid, Integer orderStatus)
    {
        List<MemberOrderDTO> list=new ArrayList<>();
        String cacheKey= redisManager.generateCacheKey(MemberOrderDTO.class,memberUuid+"-"+orderStatus);
        //从缓存读取
        String cacheData=redisManager.get(cacheKey);

        if(StringUtils.isNotBlank(cacheData)) {
            list =  KryoSerializeUtils.deserializationList(cacheData,MemberOrderDTO.class);
        }else{
            //获取失效时间，如果已经失效，再查询数据库，即使为空直，如果没失效，也不会直接访问数据库
            long expire = redisManager.getExpire(cacheKey);
            if (expire <= 0) {
                list = orderItemDubboService.getMemberOrderList(memberUuid, orderStatus);
                List<String> productIds=new ArrayList<>();
                for(MemberOrderDTO dto:list){
                    productIds.add(dto.getProductUuid());
                }
                List<Goods> goodsList= goodsDubboService.getGoodsListByIds(productIds);
                for(MemberOrderDTO dto:list){

                    for(Goods goods:goodsList){
                        if(dto.getProductUuid().equals(goods.getUuid())){
                            dto.setProductImage(goods.getGoodsImage());
                        }
                    }
                }
                if (!list.isEmpty()) {
                    //如果数据库为空，设置一个null值的失效时间，防止缓存击穿
                    cacheData=KryoSerializeUtils.serializationList(list,MemberOrderDTO.class);
                } //加入缓存
                redisManager.setByValue(cacheKey, cacheData, redisManager.getRandomExpire(10), TimeUnit.HOURS);
            }
        }
        return list;
    }
    private  PageInfo<MemberOrderDTO> pageQueryMemberOrders(MemberOrderReq req){

        PageInfo<MemberOrderDTO> pageInfo=orderItemDubboService.pageQueryMemberOrders(req);
        List<MemberOrderDTO> list=pageInfo.getList();
        List<String> productIds=new ArrayList<>();
        for(MemberOrderDTO dto:list){
            productIds.add(dto.getProductUuid());
        }
        List<Goods> goodsList= goodsDubboService.getGoodsListByIds(productIds);
        for(MemberOrderDTO dto:list){
            for(Goods goods:goodsList){
                if(dto.getProductUuid().equals(goods.getUuid())){
                    dto.setProductImage(goods.getGoodsImage());
                }
            }
        }
        return pageInfo;
    }
    @Override
    public PageInfo<MemberOrderDTO> pageGetOrderList(MemberOrderReq req) {

        PageInfo<MemberOrderDTO> pageInfo = new PageInfo<>();

        if (req.getPageNum() == null || req.getPageNum() <= 1) {
            /**从缓存获取第一屏数据***/
            String cacheKey = redisManager.generateCacheKey(MemberOrderDTO.class, "fs-" + req.getMemberUuid());
            String cacheData = redisManager.get(cacheKey);
            if (StringUtils.isNotBlank(cacheData)) {
                pageInfo = JSON.parseObject(cacheData, new TypeReference<PageInfo<MemberOrderDTO>>() {
                }.getType());
            } else {
                /**缓存第一屏数据***/
                //获取失效时间，如果已经失效，再查询数据库，即使为空直，如果没失效，也不会直接访问数据库
                long expire = redisManager.getExpire(cacheKey);
                if (expire <= 0) {
                    pageInfo = pageQueryMemberOrders(req);
                    if (pageInfo != null) {
                        cacheData = JSON.toJSONString(pageInfo);
                    }
                    redisManager.setByValue(cacheKey, cacheData, redisManager.getRandomExpire(10), TimeUnit.HOURS);
                }
            }
        } else {
            /**从数据库获取数据**/
            pageInfo = pageQueryMemberOrders(req);
        }
        return pageInfo;
    }
}
