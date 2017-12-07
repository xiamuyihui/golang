package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.dubbo.basedata.service.ClassCategoryDubboService;
import com.kingthy.response.ClassCategoryResp;
import com.kingthy.service.CategoryService;
import com.kingthy.util.CommonUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service(value = "categoryService")
public class CategoryServiceImpl implements CategoryService
{

    private static final Logger LOG= LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Reference(version = "1.0.0",timeout = 15000)
    private ClassCategoryDubboService classCategoryDubboService;


    @HystrixCommand(fallbackMethod = "queryCategoryFallback")
    @Override
    public Map<String, List<ClassCategoryResp>> queryCategory()
    {
        List<ClassCategoryResp> classCategorylist = classCategoryDubboService.queryClassCategory();


        Map<String, List<ClassCategoryResp>> classCategoryMap = new LinkedHashMap<>();


        CommonUtils.listGroup2Map(classCategorylist, classCategoryMap, ClassCategoryResp.class, "getTopName");// 输入方法名

        return classCategoryMap;
    }
    /**熔断处理****/
    private Map<String, List<ClassCategoryResp>> queryCategoryFallback(Throwable e){
        LOG.error("queryCategory 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }
}
