package com.kingthy.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kingthy.cache.RedisManager;
import com.kingthy.dubbo.basedata.service.AreaDubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kingthy.entity.Area;
import com.kingthy.service.AreaService;

/**
 * @author shenghuizhao
 */
@Service(value = "productService")
public class AreaServiceImpl implements AreaService
{


    @Reference(version = "1.0.0")
    private AreaDubboService areaDubboService;

    @Autowired
    private RedisManager redisManager;
    
    @Override
    public List<Area> getProvince()
    {

        String cacheKey= redisManager.generateCacheKey(Area.class,"Province");

        List<Area> areaList = null;

        String cacheData = redisManager.get(cacheKey);
        if(cacheData != null)
        {
            areaList = JSONObject.parseArray(cacheData,Area.class);
        }
        else
        {
            Area cond=new Area();
            cond.setGrade(0);
            areaList=areaDubboService.queryArea(cond);
            long expire = redisManager.getExpire(cacheKey);
            if (expire <= 0)
            {
                redisManager.set(cacheKey, JSON.toJSONString(areaList),redisManager.getRandomExpire(), TimeUnit.DAYS);
            }
        }
        return areaList;
    }
    
    @Override
    public List<Area> getCity(Integer grade, Integer parentId)
    {
        String cacheKey= redisManager.generateCacheKey(Area.class,parentId+""+grade);

        List<Area> areaList = null;

        String cacheData = redisManager.get(cacheKey);
        if(cacheData != null)
        {
            areaList = JSONObject.parseArray(cacheData,Area.class);
        }
        else
        {
            Area cond=new Area();
            cond.setGrade(grade == null ? 0 : grade);
            cond.setAreaParentId(parentId);
            areaList=areaDubboService.queryArea(cond);

            long expire = redisManager.getExpire(cacheKey);
            if (expire <= 0)
            {
                redisManager.set(cacheKey, JSON.toJSONString(areaList),redisManager.getRandomExpire(), TimeUnit.DAYS);
            }
        }
        return areaList;
    }
    
}
