package com.kingthy.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.kingthy.cache.RedisManager;
import com.kingthy.common.KryoSerializeUtils;
import com.kingthy.dto.UpdateCacheQueueDTO;
import com.kingthy.dubbo.user.service.ReceiverDubboService;
import com.kingthy.request.ReceiverReq;
import com.kingthy.util.BeanMapperUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kingthy.dto.MemberReceiverDTO;
import com.kingthy.entity.Receiver;
import com.kingthy.service.ReceiverService;
/**
 *
 * 会员收获地址 [业务处理实现类]
 * 
 * @author likejie 2017-4-20
 * 
 * @version 1.0.0
 *
 */
@Service("receiverService")
public class ReceiverServiceImpl implements ReceiverService
{

    //dubbo服务提供端接口
    @Reference(version = "1.0.0")
    private ReceiverDubboService receiverDubboService;

    @Autowired
    private RedisManager redisManager;


    @Override
    public Receiver getReceiverByUuid(String uuid)
    {
       return receiverDubboService.selectByUuid(uuid);
    }
    
    @Override
    public List<MemberReceiverDTO> getMemberReceiverList(String memberUuid)
    {
        List<MemberReceiverDTO> list=null;
        String cacheKey= redisManager.generateCacheKey(MemberReceiverDTO.class,memberUuid);
        //从缓存读取
        String cacheData=redisManager.get(cacheKey);
        if(StringUtils.isNotBlank(cacheData)) {
            list =  KryoSerializeUtils.deserializationList(cacheData,MemberReceiverDTO.class);
        }else{
            //获取失效时间，如果已经失效，再查询数据库，即使为空直，如果没失效，也不会直接访问数据库
            long expire = redisManager.getExpire(cacheKey);
            if (expire <= 0) {
                list=receiverDubboService.getMemberReceiverList(memberUuid);
                if (!list.isEmpty()) {
                    //如果数据库为空，设置一个null值的失效时间，防止缓存击穿
                    cacheData= KryoSerializeUtils.serializationList(list,MemberReceiverDTO.class);
                }  //加入缓存
                redisManager.set(cacheKey, cacheData, redisManager.getRandomExpire(20), TimeUnit.DAYS);
            }
        }
        return list;
    }
    @Override
    public int addReceiver(Receiver receiver)
    {
        // 设置指定地址为默认地址
        int res = receiverDubboService.insert(receiver);
        if (res > 0)
        {
            //更新缓存
            String cacheKey= redisManager.generateCacheKey(MemberReceiverDTO.class,receiver.getMemberUuid());
            UpdateCacheQueueDTO updateCacheQueueDTO=new UpdateCacheQueueDTO();
            updateCacheQueueDTO.setCacheKey(cacheKey);
            redisManager.updateCache(updateCacheQueueDTO);
        }
        return res;
    }
    @Override
    public int updateReceiver(Receiver receiver)
    {
        int res= receiverDubboService.updateByUuid(receiver);
        if(res>0){
            //更新缓存
            String cacheKey= redisManager.generateCacheKey(MemberReceiverDTO.class,receiver.getMemberUuid());
            UpdateCacheQueueDTO updateCacheQueueDTO=new UpdateCacheQueueDTO();
            updateCacheQueueDTO.setCacheKey(cacheKey);
            redisManager.updateCache(updateCacheQueueDTO);
        }
        return  res;

    }
    
    @Override
    public int deleteReceiver(String uuid,String memberUuid)
    {
        int res= receiverDubboService.deleteReceiver(uuid,memberUuid);
        if(res>0){
            //更新缓存
            String cacheKey= redisManager.generateCacheKey(MemberReceiverDTO.class,memberUuid);
            UpdateCacheQueueDTO updateCacheQueueDTO=new UpdateCacheQueueDTO();
            updateCacheQueueDTO.setCacheKey(cacheKey);
            redisManager.updateCache(updateCacheQueueDTO);
        }
        return 0;
    }
    @Override
    public int setDefaultReceiver(String memberUuid, String uuid)
    {

        int res = receiverDubboService.setDefaultReceiver(memberUuid, uuid);
        if (res > 0)
        {
            //更新缓存
            String cacheKey= redisManager.generateCacheKey(MemberReceiverDTO.class,memberUuid);
            UpdateCacheQueueDTO updateCacheQueueDTO=new UpdateCacheQueueDTO();
            updateCacheQueueDTO.setCacheKey(cacheKey);
            redisManager.updateCache(updateCacheQueueDTO);
        }
        return res;

    }
    
    @Override
    public Receiver getDefaultReceiver(String memberUuid)
    {
        return receiverDubboService.getDefaultReceiver(memberUuid);
    }
    @Override
    public PageInfo<Receiver> pageGetReceiverList(ReceiverReq req){

        Receiver cond=new Receiver();
        cond.setMemberUuid(req.getMemberUuid());
        cond.setDelFlag(false);
        PageInfo<Receiver> pageInfo= receiverDubboService.queryPage(req.getPageNum(),req.getPageSize(),cond);
        return pageInfo;
    }
}
