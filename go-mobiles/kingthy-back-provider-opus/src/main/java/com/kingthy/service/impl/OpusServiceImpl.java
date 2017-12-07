package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kingthy.cache.RedisManager;
import com.kingthy.constant.CacheMqNameConstans;
import com.kingthy.dto.OpusDto;
import com.kingthy.dto.UpdateCacheQueueDTO;
import com.kingthy.dubbo.basedata.service.AccessoriesDubboService;
import com.kingthy.dubbo.basedata.service.MaterialDubboService;
import com.kingthy.dubbo.basedata.service.PartsCategoryDubboService;
import com.kingthy.dubbo.opus.service.OpusDubboService;
import com.kingthy.dubbo.order.service.SnDubboService;
import com.kingthy.entity.*;
import com.kingthy.exception.BizException;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.OpusService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * OpusServiceImpl(作品的接口实现类)
 * <p>
 * @author 赵生辉 2017年05月04日 17:24
 *
 * @version 1.0.0
 */
@Service(value="opusService")
public class OpusServiceImpl implements OpusService
{

    @Reference(version ="1.0.0")
    private OpusDubboService opusDubboService;

    @Reference(version ="1.0.0")
    private AccessoriesDubboService accessoriesDubboService;

    @Reference(version ="1.0.0")
    private MaterialDubboService materialDubboService;

    @Reference(version ="1.0.0")
    private PartsCategoryDubboService partsCategoryDubboService;

    @Reference(version ="1.0.0")
    private SnDubboService snDubboService;

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final int VERSION = 1;

    private static final Logger LOG= LoggerFactory.getLogger(OpusServiceImpl.class);

    /**
     * 获取sn
     * @return
     */
    private String getSn(){

        WebApiResponse<String> sn = snDubboService.generateSn(Sn.Type.opus.name());
        return sn.getCode() != WebApiResponse.SUCCESS_CODE ? null : sn.getData();
    }

    @HystrixCommand(fallbackMethod = "createOpusFallback")
    @Override
    public String createOpus(Opus opus)
    {
        String sn = getSn();
        opus.setSn(sn);
        return opusDubboService.insertReturn(opus);
    }

    private String createOpusFallback(Opus opus,Throwable e){
        LOG.error("list 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }

    @Override
    public List<Opus> queryOpusList(Opus opus)
    {
        return opusDubboService.select(opus);
    }

    @Override
    public OpusDto queryOpusInfo(String uuid)
    {

        String cacheKey= redisManager.generateCacheKey(OpusDto.class, uuid);

        OpusDto opusDto = null;

        String cacheData = redisManager.get(cacheKey);
        if(!StringUtils.isEmpty(cacheData)){
            opusDto = JSONObject.parseObject(cacheData,OpusDto.class);
        }
        else
        {
            long expire = redisManager.getExpire(cacheKey);
            if (expire <= 0)
            {
                Opus opus = new Opus();
                opus.setUuid(uuid);
                opus = opusDubboService.selectOne(opus);
                opusDto = JSONObject.parseObject(JSON.toJSONString(opus),OpusDto.class);

                List<String> accessoriesStringList = JSON.parseArray(opus.getOpusAccessoriesInfo(),String.class);
                if(accessoriesStringList != null && !accessoriesStringList.isEmpty())
                {
                    List<Accessories> accessoriesList = accessoriesDubboService.findAccessories(accessoriesStringList);
                    opusDto.setOpusAccessoriesList(accessoriesList);
                }

                List<String> materialStringList = JSON.parseArray(opus.getOpusMaterialInfo(),String.class);
                if(materialStringList != null && !materialStringList.isEmpty())
                {
                    List<Material> materialList = materialDubboService.findMaterial(materialStringList);
                    opusDto.setOpusMaterialList(materialList);
                }

                List<String> partsCategoryStringList = JSON.parseArray(opus.getOpusPartsInfo(),String.class);
                if(partsCategoryStringList != null && !partsCategoryStringList.isEmpty())
                {
                    List<PartsCategory> partsCategoryList = partsCategoryDubboService.findPartsCategory(partsCategoryStringList);
                    opusDto.setOpusPartList(partsCategoryList);
                }

                redisManager.set(cacheKey, JSON.toJSONString(opusDto),redisManager.getRandomExpire(), TimeUnit.DAYS);
            }
        }
        if(opusDto == null)
        {
            throw new BizException("查询作品失败");
        }
        return opusDto;
    }

    @Override
    public Opus queryOpus(String uuid, String memberUuid)
    {
        Opus opus = new Opus();
        opus.setUuid(uuid);
        opus.setMemberUuid(memberUuid);
        return opusDubboService.selectOne(opus);
    }

    @HystrixCommand(fallbackMethod = "deleteFallback")
    @Override
    public int delete(List<String> list, String memberUuid)
    {

        for(String uuid :list)
        {//先删除缓存的数据
            String cacheKey = redisManager.generateCacheKey(OpusDto.class, uuid);
            UpdateCacheQueueDTO queue=new UpdateCacheQueueDTO();
            queue.setCacheKey(cacheKey);
            rabbitTemplate.convertAndSend(CacheMqNameConstans.EXCHANGE_NAME,CacheMqNameConstans.ROUTING_KEY_NAME, queue);
        }
        return opusDubboService.deleteByUuids(list,memberUuid);
        //调用dubbo服务执行删除操作
    }

    private int deleteFallback(List<String> list,Throwable e){
        LOG.error("list 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }

    @HystrixCommand(fallbackMethod = "updateOpusFallback")
    @Override
    public int updateOpus(Opus opus)
    {
        Date date = new Date();
        opus.setModifyDate(date);
        int result = opusDubboService.updateByUuid(opus);
        //调用dubbo服务更新作品
        if(result != 0){
            //更新消息后更新缓存信息
            String cacheKey= redisManager.generateCacheKey(OpusDto.class, opus.getUuid());
            long expire = redisManager.getExpire(cacheKey);
            if (expire <= 0)
            {
                Opus opusBase = new Opus();
                opusBase.setUuid(opus.getUuid());
                opusBase = opusDubboService.selectOne(opusBase);
                OpusDto opusDto = JSONObject.parseObject(JSON.toJSONString(opusBase),OpusDto.class);
                redisManager.set(cacheKey, JSON.toJSONString(opusDto),redisManager.getRandomExpire(), TimeUnit.DAYS);
            }
        }
        return result;
    }

    private int updateOpusFallback(Opus opus,Throwable e){
        LOG.error("list 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }

    @Override
    public int opusAmount(String userUuid)
    {
        Opus opus = new Opus();
        opus.setMemberUuid(userUuid);
        return opusDubboService.selectCount(opus);
        /*调用dubbo服务查询作品数量*/
    }
}
