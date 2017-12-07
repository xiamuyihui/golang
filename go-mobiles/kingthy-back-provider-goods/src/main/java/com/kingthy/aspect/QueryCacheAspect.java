package com.kingthy.aspect;

import com.kingthy.annotation.QueryCache;
import com.kingthy.annotation.QueryCacheKey;
import com.kingthy.cache.RedisManager;
import com.kingthy.common.CacheNameSpace;
import com.kingthy.dto.RedisCacheDTO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.SynthesizingMethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 15:18 on 2017/11/15.
 * @Modified by:
 */
@Service
@Aspect
public class QueryCacheAspect
{
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryCacheAspect.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisManager redisManager;

    /**
     * 切入点: 有@QueryCache注解的方法
     */
    @Pointcut("@annotation(com.kingthy.annotation.QueryCache)")
    public void cachePointcut(){
        System.out.println();
    }

    @Around("cachePointcut()")
    public Object proceed(ProceedingJoinPoint joinPoint) throws Throwable{

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        Method method = methodSignature.getMethod();

        QueryCache queryCache = method.getAnnotation(QueryCache.class);

        CacheNameSpace nameSpace = queryCache.value();

        String key = null;

        StringBuffer subkey = new StringBuffer();

        for (int m = 0; m < joinPoint.getArgs().length; m++) {

            MethodParameter methodParameter = new SynthesizingMethodParameter(method, m);

            if (null != methodParameter.getParameterAnnotation(QueryCacheKey.class)){
                subkey.append(joinPoint.getArgs()[m]);
                subkey.append("_");
            }

        }

        if (!StringUtils.isEmpty(subkey)){
            key = nameSpace + ":" + subkey.substring(0, subkey.length() -1);
        }

        if (StringUtils.isEmpty(key)){
            LOGGER.error("redis缓存key找不到");
            throw new Exception("redis缓存key找不到");
        }

//        Gson gson = new Gson();

        ValueOperations<String, RedisCacheDTO> valueOperations = redisTemplate.opsForValue();

        if (redisTemplate.hasKey(key)){

            RedisCacheDTO redisCacheDTO = valueOperations.get(key);

            return redisCacheDTO.getValue();
//            return gson.fromJson(stringRedisTemplate.opsForValue().get(key), resultType.newInstance().getClass());
        }

        Object object = joinPoint.proceed();

        RedisCacheDTO r = new RedisCacheDTO();
        r.setValue(object);
//        stringRedisTemplate.opsForValue().set(key, gson.toJson(object), redisManager.getRandomExpire(), TimeUnit.DAYS);
        valueOperations.set(key, r, redisManager.getRandomExpire() * 24 * 3600, TimeUnit.SECONDS);

        return object;

    }
}
