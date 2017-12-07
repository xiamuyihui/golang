package com.kingthy.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kingthy.response.ClassCategoryResp;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.CategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping("/category")
public class CategoryController
{
    @Autowired
    CategoryService categoryService;
    
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(CategoryController.class);
    
    @ApiOperation(value = "获取所有分类信息", notes = "")
    @GetMapping
    
    public WebApiResponse<?> queryGoods()
    {
        Map<String, List<ClassCategoryResp>> classCategories = null;
        try
        {
            classCategories = categoryService.queryCategory();
            
        }
        catch (Exception e)
        {
            LOG.error("/category:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
        return WebApiResponse.success(classCategories);
        
    }
}
