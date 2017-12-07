package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.dubbo.basedata.service.StyleCategoryDubboService;
import com.kingthy.response.ClassCategoryResp;
import com.kingthy.service.StyleCategoryService;
import com.kingthy.util.CommonUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service(value = "styleCategoryService")
public class StyleCategoryServiceImpl implements StyleCategoryService
{
    private static final Logger LOG= LoggerFactory.getLogger(StyleCategoryServiceImpl.class);

    @Reference(version = "1.0.0")
    private StyleCategoryDubboService styleCategoryDubboService;


    @HystrixCommand(fallbackMethod = "queryStyleCategoryFallback")
    @Override
    public Map<String, List<ClassCategoryResp>> queryStyleCategory()
    {
        List<ClassCategoryResp> styleCategorylist = styleCategoryDubboService.queryStyleClassCategory();
        Map<String, List<ClassCategoryResp>> classCategoryMap = new LinkedHashMap<>();
        CommonUtils.listGroup2Map(styleCategorylist, classCategoryMap, ClassCategoryResp.class, "getTopName");// 输入方法名
        return classCategoryMap;
    }
    /**熔断处理****/
    private Map<String, List<ClassCategoryResp>> queryStyleCategoryFallback(Throwable e){
        LOG.error("queryCategory 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }
}
