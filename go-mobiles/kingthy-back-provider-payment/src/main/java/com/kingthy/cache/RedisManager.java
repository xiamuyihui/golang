package com.kingthy.cache;

import com.kingthy.constant.CacheMqNameConstans;
import com.kingthy.dto.UpdateCacheQueueDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 描述：缓存操作
 * @author  likejie
 * @date 2017.11.1
 */
@Component
public class RedisManager {


    @Autowired
    private  StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /***针对空值到缓存设置一个过期时间***/
    @Value("${cache.nullValueExpire}")
    private long nullValueExpire;

    public long getNullValueExpire() {
        return nullValueExpire;
    }
    /**
     *
     * 给缓存key设置一个null值，并设置失效时间，预防缓存击穿
     *
     */
    public   void setNull(String key) {

        if (StringUtils.isNotBlank(key)) {
            stringRedisTemplate.opsForValue().set(key, "",this.nullValueExpire,TimeUnit.MINUTES);
        }
    }
    public String generateCacheKey(Class objectClass,String key) {

        //控制长度
        String className = objectClass.getSimpleName();
        int max=20;
        if (className.length() > max) {
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

    public synchronized void set(String key, String value, int time) {
        if (StringUtils.isNotBlank(key)) {
            stringRedisTemplate.opsForValue().set(key, value, time);
        }

    }

    public synchronized void set(String key, String value, int time, TimeUnit timeUint) {
        if (StringUtils.isNotBlank(key)) {
            stringRedisTemplate.opsForValue().set(key, value, time, timeUint);
        }
    }
    /**
     * 设置缓存，如果缓存为null，给null设置缓存失效时间。
     *
     */
    public synchronized void setByValue(String key, String value, int time, TimeUnit timeUint) {

        if (StringUtils.isNotBlank(key)) {
            if (StringUtils.isBlank(value)) {
                //给缓存key设置一个null值，并设置失效时间，预防缓存击穿
                setNull(key);
            } else {
                stringRedisTemplate.opsForValue().set(key, value, time, timeUint);
            }
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
        return new HashSet<>();
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
    public int getRandomExpire(){
        Random rd=new Random();
        return rd.nextInt(10);
    }
   /**
    *
    * 获取一个随机数，设置缓存超时时间
    */
   public int getRandomExpire(Integer count){
       Random rd=new Random();
       return rd.nextInt(count);
   }
   /**
    *
    * 更新缓存
    */
   public void updateCache(UpdateCacheQueueDTO updateCacheQueueDTO){

       //缓存key
       String cacheKey=updateCacheQueueDTO.getCacheKey();

       if(stringRedisTemplate.hasKey(cacheKey)){
           //缓存数据
           String cacheData=get(cacheKey);
           //过期时间
           long expire = getExpire(cacheKey);
           //检查是否需要更新缓存
           Boolean isUpdate=StringUtils.isNotBlank(cacheData)||(cacheData==null&&expire>0);
           if(isUpdate){
               //发送消息到mq，让mq执行更新操作
               UpdateCacheQueueDTO queue=new UpdateCacheQueueDTO();
               queue.setCacheKey(cacheKey);
               rabbitTemplate.convertAndSend(CacheMqNameConstans.EXCHANGE_NAME,CacheMqNameConstans.ROUTING_KEY_NAME,updateCacheQueueDTO);
           }
       }


   }
}
