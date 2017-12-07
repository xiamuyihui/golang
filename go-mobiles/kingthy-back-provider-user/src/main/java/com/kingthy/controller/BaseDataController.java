package com.kingthy.controller;

import com.alibaba.fastjson.JSON;
import com.kingthy.entity.BaseData;
import com.kingthy.request.BaseDataReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.BaseDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * BaseDataController(描述其作用)
 * <p>
 * @author 赵生辉 2017年05月11日 9:55
 *
 * @version 1.0.0
 */
@Api
@RestController
@RequestMapping("baseData")
public class BaseDataController
{

    @Autowired
    private BaseDataService baseDataService;

    @ApiOperation("查询基础数据")
    @PostMapping("")
    public WebApiResponse<?> queryBaseData(@RequestBody BaseDataReq baseDataReq)
    {
        BaseData baseData = JSON.parseObject(JSON.toJSONString(baseDataReq), BaseData.class);
        List<BaseData> list = baseDataService.queryBaseData(baseData);

        if (list != null && !list.isEmpty())
        {
            return WebApiResponse.success(list);
        }
        else
        {
            return WebApiResponse.error("没有找到数据");
        }
    }

    @ApiOperation("添加基础数据")
    @PostMapping("/create")
    public WebApiResponse<?> createBaseData(@RequestBody BaseData baseData)
    {

        int result = baseDataService.createBaseData(baseData);

        if (result != 0)
        {
            return WebApiResponse.success(result);
        }
        else
        {
            return WebApiResponse.error("没有找到数据");
        }
    }
}
