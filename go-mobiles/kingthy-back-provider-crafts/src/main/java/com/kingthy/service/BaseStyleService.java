package com.kingthy.service;

import com.github.pagehelper.PageInfo;
import com.kingthy.dto.BaseStyleFileDto;
import com.kingthy.entity.BaseStyle;
import com.kingthy.request.BaseStylePageReq;

import java.util.List;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name BaseStyleDubboService
 * @description 服装款式接口
 * @create 2017/9/7
 */
public interface BaseStyleService {
    List<BaseStyle> selectAll();

    BaseStyle selectByUuid(String uuid);

    PageInfo<BaseStyle> queryPage(BaseStylePageReq baseStylePageReq);

    List<BaseStyleFileDto> findFiles();
}
