package com.kingthy.cache;

import com.kingthy.conf.CacheSetting;
import com.kingthy.constant.CacheMqNameConstans;
import com.kingthy.dto.UpdateCacheQueueDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 缓存操作
 * Created by likejie on 2017/6/1.
 */
@Component
public class RedisManager {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Autowired
    private CacheSetting cacheSetting;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    /**
     *
     * 给空值的缓存对象设置一个超时时间，预防缓存击穿
     */
    public long getNullValueExpire(){

        return cacheSetting.getNullValueExpire();
    }
    /**
     *
     * 给缓存key设置一个null值，并设置失效时间，预防缓存击穿
     *
     */
    public  void setNull(String key) {

        if (StringUtils.isNotBlank(key)) {
            stringRedisTemplate.opsForValue().set(key, null,cacheSetting.getNullValueExpire(),TimeUnit.MINUTES);
        }
    }
    public String generateCacheKey(Class objectClass,String key) {

        //控制长度
        String className = objectClass.getSimpleName();
        if (className.length() > 20) {
            className = className.substring(0, 20);
        }
        return className +":"+ key;
    }

    public synchronized String get(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return stringRedisTemplate.opsForValue().get(key);
    }

    public synchronized void set(String key, String value) {

        if (StringUtils.isNotBlank(key)) {
            stringRedisTemplate.opsForValue().set(key, value);
        }
    }

    public synchronized void set(String key, String value, long time) {
        if (StringUtils.isNotBlank(key)) {
            stringRedisTemplate.opsForValue().set(key, value, time);
        }

    }

    public synchronized void set(String key, String value, long time, TimeUnit timeUint) {
        if (StringUtils.isNotBlank(key)) {
            stringRedisTemplate.opsForValue().set(key, value, time, timeUint);
        }

    }

    public synchronized void delete(String key) {
        if (StringUtils.isNotBlank(key)) {
            stringRedisTemplate.delete(key);
        }
    }

    public  Set<String> keys(String key) {
        if (StringUtils.isNotBlank(key)) {
            return stringRedisTemplate.keys(key);
        }
        return null;
    }

    public  long getExpire(String key) {
        if (StringUtils.isNotBlank(key)) {
            return stringRedisTemplate.getExpire(key);
        }
        return 0;
    }

    public  void expire(String key, long time, TimeUnit timeUnit) {
        if (StringUtils.isNotBlank(key)) {
            stringRedisTemplate.expire(key, time, timeUnit);
        }

    }
   /**
    *
    * 获取一个随机数，设置缓存超时时间
    */
   public int getRandomExpire(){
       Random rd=new Random();
       int val=rd.nextInt(10);
       return val;
   }

   /**
    *
    * 更新缓存
    */
   public void UpdateCache(UpdateCacheQueueDTO updateCacheQueueDTO){

       //缓存key
       String cacheKey=updateCacheQueueDTO.getCacheKey();

       if(stringRedisTemplate.hasKey(cacheKey)){
           //缓存数据
           String cacheData=get(cacheKey);
           //过期时间
           long expire = getExpire(cacheKey);
           //检查是否需要更新缓存
           if(StringUtils.isNotBlank(cacheData)||(cacheData==null&&expire>0)){
               //发送消息到mq，让mq执行更新操作
               UpdateCacheQueueDTO queue=new UpdateCacheQueueDTO();
               queue.setCacheKey(cacheKey);
               rabbitTemplate.convertAndSend(CacheMqNameConstans.EXCHANGE_NAME,CacheMqNameConstans.ROUTING_KEY_NAME,updateCacheQueueDTO);
           }
       }


   }
}
