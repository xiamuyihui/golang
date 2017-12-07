package com.kingthy.conf;

import com.kingthy.common.CommonUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 13:36 on 2017/10/27.
 * @Modified by:
 */
//@Component
public class AuthHeaderFilter extends ZuulFilter
{
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        RequestContext requestContext = RequestContext.getCurrentContext();
        requestContext.addZuulRequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER, requestContext.getRequest().getHeader("Authorization"));
        return null;
    }
}
