package com.kingthy.service;

import java.util.List;
import java.util.Map;

import com.kingthy.response.ClassCategoryResp;

@FunctionalInterface
public interface StyleCategoryService
{
    
    Map<String, List<ClassCategoryResp>> queryStyleCategory();
    
}
