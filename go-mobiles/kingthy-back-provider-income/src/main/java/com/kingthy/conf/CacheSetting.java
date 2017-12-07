package com.kingthy.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 缓存设置
 * @author Created by likejie on 2017/5/25.
 *
 *
 */
@Component
@ConfigurationProperties(prefix = "cache.expire")
public class CacheSetting {



    /***针对空值到缓存设置一个过期时间***/
    private long nullValueExpire;


    public long getNullValueExpire() {
        return nullValueExpire;
    }

    public void setNullValueExpire(long nullValueExpire) {
        this.nullValueExpire = nullValueExpire;
    }
}
