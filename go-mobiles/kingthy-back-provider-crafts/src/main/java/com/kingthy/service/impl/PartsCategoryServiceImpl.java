package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.kingthy.dto.PartsFileDto;
import com.kingthy.dubbo.basedata.service.PartsCategoryDubboService;
import com.kingthy.entity.PartsCategory;
import com.kingthy.service.PartsCategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * PartsCategoryServiceImpl
 *
 * 赵生辉 2017年3月29日 下午1:56:03
 *
 * @version 1.0.0
 *
 */
@Service(value = "partsCategoryService")
public class PartsCategoryServiceImpl implements PartsCategoryService
{
    // 默认版本号
    private static final int VERSION = 1;

    @Reference(version = "1.0.0")
    private PartsCategoryDubboService partsCategoryDubboService;


    @Override
    public  PartsCategory findPartsCategory(String uuid){
        return  partsCategoryDubboService.selectByUuid(uuid);
    }
    @Override
    public List<PartsCategory> findAllPartsCategory()
    {
        PartsCategory cond=new PartsCategory();
        cond.setDelFlag(false);
        List<PartsCategory> countries = partsCategoryDubboService.select(cond);
        if (countries.size() > 0)
        {
            return countries;
        }
        else
        {
            return new ArrayList<PartsCategory>();
        }

    }
    @Override
    public PageInfo<PartsCategory> findAllPartsCategoryPage(int pageNo, int pageSize, PartsCategory partsCategory)
    {
        partsCategory.setDelFlag(false);
        return partsCategoryDubboService.queryPage(pageNo,pageSize,partsCategory);
    }

    @Override
    public List<PartsFileDto> findFiles() {
        return partsCategoryDubboService.findFiles();
    }
}
