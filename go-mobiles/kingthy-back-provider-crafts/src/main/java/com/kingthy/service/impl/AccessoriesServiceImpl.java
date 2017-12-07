package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.kingthy.dto.AccessoriesDto;
import com.kingthy.dto.AccessoriesFileDto;
import com.kingthy.dubbo.basedata.service.AccessoriesDubboService;
import com.kingthy.entity.Accessories;
import com.kingthy.service.AccessoriesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "accessoriesService")
public class AccessoriesServiceImpl implements AccessoriesService {
    @Reference(version = "1.0.0", timeout = 3000)
    private AccessoriesDubboService accessoriesDubboService;


    @Override
    public AccessoriesDto findAccessories(String accessoriesUuid) {
        AccessoriesDto accessoriesDto = accessoriesDubboService.findAccessories(accessoriesUuid);
        if (accessoriesDto != null && null != accessoriesDto.getUuid()) {
            return accessoriesDto;
        } else {
            return null;
        }
    }

    @Override
    public PageInfo<AccessoriesDto> findAccessoriesPage(int pageNum, int pageSize, Accessories accessories) {
        return accessoriesDubboService.findAccessoriesPage(pageNum, pageSize, accessories);
    }

    @Override
    public List<AccessoriesFileDto> findFiles() {
        return accessoriesDubboService.findFiles();
    }
}
