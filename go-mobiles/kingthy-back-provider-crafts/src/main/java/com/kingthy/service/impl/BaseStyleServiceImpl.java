package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.kingthy.dto.BaseStyleFileDto;
import com.kingthy.dubbo.basedata.service.BaseStyleDubboService;
import com.kingthy.entity.BaseStyle;
import com.kingthy.request.BaseStylePageReq;
import com.kingthy.service.BaseStyleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name BaseStyleServiceImpl
 * @description 服装款式接口实现类
 * @create 2017/9/7
 */
@Service("clothingStyleService")
public class BaseStyleServiceImpl implements BaseStyleService {

    @Reference(version = "1.0.0",timeout = 3000)
    private BaseStyleDubboService baseStyleDubboService;

    @Override
    public List<BaseStyle> selectAll() {
        return baseStyleDubboService.selectAll();
    }

    @Override
    public BaseStyle selectByUuid(String uuid) {
        return baseStyleDubboService.selectByUuid(uuid);
    }

    @Override
    public PageInfo<BaseStyle> queryPage(BaseStylePageReq baseStylePageReq) {
        return baseStyleDubboService.queryPage(baseStylePageReq);
    }

    @Override
    public List<BaseStyleFileDto> findFiles() {
        return baseStyleDubboService.findFiles();
    }
}
