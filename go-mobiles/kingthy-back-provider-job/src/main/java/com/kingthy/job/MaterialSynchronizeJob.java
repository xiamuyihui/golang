package com.kingthy.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingthy.util.HttpClientHelper;

/**
 * MaterialSynchronizeJob
 * <p>
 * @author yuanml 2017/9/15
 *
 * @version 1.0.0
 */
@Component
@Configurable
public class MaterialSynchronizeJob
{
    
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    @Value("${url.syncUrl}")
    private String apiUrlString;
    
    @Scheduled(cron = "0 3 * * * *")
    public void materialSynchronize()
    {
        Map<String, Object> map = new HashMap<>(16);
        List<String> list = new ArrayList<>();
        list.add("M");
        list.add("F");
        for (String type : list)
        {
            map.put("type", type);
            String resultData = HttpClientHelper.sendPost(apiUrlString, map, "utf-8");
            JSONObject x = JSON.parseObject(resultData);
            JSONArray jsonArray = (JSONArray)x.get("data");
            for (int j = 0; j < jsonArray.size(); j++)
            {
                JSONObject jsonObject = (JSONObject)jsonArray.get(j);
                String key = type + ":" + jsonObject.get("code");
                String value = jsonObject.get("price").toString();
                stringRedisTemplate.opsForValue().set(key, value);
            }
        }
    }
}
