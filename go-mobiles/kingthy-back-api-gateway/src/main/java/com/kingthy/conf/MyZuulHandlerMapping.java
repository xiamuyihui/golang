package com.kingthy.conf;

import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.web.ZuulController;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 17:50 on 2017/10/27.
 * @Modified by:
 */
public class MyZuulHandlerMapping extends ZuulHandlerMapping {


    public MyZuulHandlerMapping(RouteLocator routeLocator, ZuulController zuul) {
        super(routeLocator, zuul);
    }

    public void test(){
//        super.lookupHandler();
    }
}
