package com.kingthy.service;

import com.github.pagehelper.PageInfo;
import com.kingthy.dto.AccessoriesDto;
import com.kingthy.dto.AccessoriesFileDto;
import com.kingthy.entity.Accessories;

import java.util.List;

public interface AccessoriesService {

    /**
     * findAccessories(查询一个辅料的详情) (查询一个辅料的详情)
     *
     * @param accessoriesUuid
     * @return AccessoriesDto
     */
    Accessories findAccessories(String accessoriesUuid);

    /**
     * 分页查询辅料表
     *
     * @param pageNum
     * @param pageSize
     * @param accessories
     * @return
     */
    PageInfo<AccessoriesDto> findAccessoriesPage(int pageNum, int pageSize, Accessories accessories);

    /**
     * 查询辅料附属文件
     * @return
     */
    List<AccessoriesFileDto> findFiles();
}
