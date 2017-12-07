package com.kingthy.annotation;

import com.kingthy.common.CacheNameSpace;

import java.lang.annotation.*;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 14:48 on 2017/11/15.
 * @Modified by:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface QueryCache{
    /**
     * nameSpace
     * @return
     */
    CacheNameSpace value();
}
