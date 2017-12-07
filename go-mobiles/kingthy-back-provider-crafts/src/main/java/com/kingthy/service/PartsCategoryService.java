package com.kingthy.service;

import com.github.pagehelper.PageInfo;
import com.kingthy.dto.PartsFileDto;
import com.kingthy.entity.PartsCategory;

import java.util.List;

/**
 * 
 *
 * PartsCategoryService(部件分类接口)
 * 
 * chenz 2017年9月6日 下午1:56:17
 * 
 * @version 1.0.0
 *
 */
public interface PartsCategoryService
{
    PageInfo<PartsCategory> findAllPartsCategoryPage(int pageNo, int pageSize, PartsCategory partsCategory);

    PartsCategory findPartsCategory(String uuid);

    List<PartsCategory> findAllPartsCategory();

    List<PartsFileDto> findFiles();
}
