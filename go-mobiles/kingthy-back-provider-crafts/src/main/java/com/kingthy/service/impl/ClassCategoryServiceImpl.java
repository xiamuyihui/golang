package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.dto.CategoryTreeDto;
import com.kingthy.dubbo.basedata.service.ClassCategoryDubboService;
import com.kingthy.entity.ClassCategory;
import com.kingthy.service.ClassCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "classCategoryService")
public class ClassCategoryServiceImpl implements ClassCategoryService
{
    /**
     * 顶级节点的父节点id为"0"
     */
    private final String topNode = "0";

    @Reference(version = "1.0.0")
    private  ClassCategoryDubboService categoryDubboService;

    @Override
    public List<ClassCategory> findAllTopNodes()
    {
        ClassCategory classCategory=new ClassCategory();
        classCategory.setParentId(topNode);
        classCategory.setDelFlag(false);
        List<ClassCategory>list=categoryDubboService.select(classCategory);
        return list;
    }

    @Override
    public List<ClassCategory> findAllNodes()
    {
        ClassCategory classCategory=new ClassCategory();
        classCategory.setDelFlag(false);
        List<ClassCategory>list=categoryDubboService.select(classCategory);
        return list;
    }

    @Override
    public List<ClassCategory> findAllChildNodes(String classCategoryUuid)
    {
        ClassCategory classCategory=new ClassCategory();
        classCategory.setDelFlag(false);
        classCategory.setParentId(classCategoryUuid);
        List<ClassCategory>list=categoryDubboService.select(classCategory);
        return list;
    }


    @Override
    public int findAllChildNodesNum(String classCategoryUuid)
    {
        ClassCategory classCategory = new ClassCategory();
        classCategory.setDelFlag(false);
        classCategory.setParentId(classCategoryUuid);
        int childrenNum = categoryDubboService.selectCount(classCategory);
        return childrenNum;
    }

    @Override
    public ClassCategory findClassCategoryByUuid(String classCategoryUuid)
    {
        ClassCategory classCategory=new ClassCategory();
        classCategory.setUuid(classCategoryUuid);
        List<ClassCategory> classCategoryList = categoryDubboService.select(classCategory);
        if (classCategoryList != null && classCategoryList.size() > 0)
        {
            return classCategoryList.get(0);
        }

        return null;
    }

    @Override
    public List<ClassCategory> findAll()
    {
        ClassCategory classCategory=new ClassCategory();
        classCategory.setDelFlag(false);
        List<ClassCategory> classCategoryList = categoryDubboService.select(classCategory);
        return classCategoryList;
    }
    @Override
    public List<CategoryTreeDto> getTree()
    {
        List<CategoryTreeDto> treeList = categoryDubboService.getTree();
        return treeList;
    }

}
