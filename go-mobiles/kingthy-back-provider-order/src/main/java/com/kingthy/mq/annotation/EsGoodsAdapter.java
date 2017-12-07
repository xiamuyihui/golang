package com.kingthy.mq.annotation;

import java.lang.annotation.*;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 11:25 on 2017/6/1.
 * @Modified by:
 */

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EsGoodsAdapter{

}
