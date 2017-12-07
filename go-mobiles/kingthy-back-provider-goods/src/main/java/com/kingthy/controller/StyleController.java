package com.kingthy.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kingthy.response.ClassCategoryResp;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.StyleCategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping("/style")
public class StyleController
{
    @Autowired
    StyleCategoryService styleCategoryService;
    
    private static final Logger LOG = LoggerFactory.getLogger(StyleController.class);
    
    @ApiOperation(value = "获取所有风格分类信息", notes = "")
    @GetMapping
    public WebApiResponse queryGoods()
    {
        Map<String, List<ClassCategoryResp>> styleCategories = null;
        try
        {
            styleCategories = styleCategoryService.queryStyleCategory();
        }
        catch (Exception e)
        {
            LOG.error("/style:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
        
        return WebApiResponse.success(styleCategories);
        
    }
}
