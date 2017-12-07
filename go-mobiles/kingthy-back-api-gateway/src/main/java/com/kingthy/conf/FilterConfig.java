package com.kingthy.conf;

import com.kingthy.filter.HTTPBearerAuthorizeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * User:pany <br>
 * Date:2016-12-23 <br>
 * Time:16:59 <br>
 */

@Configuration
@EnableZuulProxy
public class FilterConfig
{

    @Bean
    public HTTPBearerAuthorizeFilter accessFilter()
    {
        test();
        return new HTTPBearerAuthorizeFilter();
    }

    @Autowired
    private ZuulHandlerMapping zuulHandlerMapping;

    public void test(){
        /*ZuulHandlerMapping mapping = new ZuulHandlerMapping(null, null);
        mapping.
        System.out.println("--------------");*/

//        MyZuulHandlerMapping myZuulHandlerMapping = new MyZuulHandlerMapping();
    }
}