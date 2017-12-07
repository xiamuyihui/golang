package com.kingthy.annotation;

import java.lang.annotation.*;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 16:33 on 2017/11/15.
 * @Modified by:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Documented
public @interface QueryCacheKey {
}
