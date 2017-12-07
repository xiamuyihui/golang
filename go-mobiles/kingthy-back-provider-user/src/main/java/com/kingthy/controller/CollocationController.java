package com.kingthy.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.kingthy.entity.Collocation;
import com.kingthy.request.CreateCollocationReq;
import com.kingthy.request.QueryCollocationReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.CollocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CollocationController(描述其作用)
 * <p>
 * @author 赵生辉 2017年05月23日 15:16
 *
 * @version 1.0.0
 */
@Api
@RestController
@RequestMapping("/collocation")
public class CollocationController
{
    private static final Logger LOG = LoggerFactory.getLogger(CollocationController.class);

    @Autowired
    private CollocationService collocationService;

    @ApiOperation(value = "创建新的潮搭")
    @PostMapping("/create")
    public WebApiResponse<?> createCollocation(@RequestBody CreateCollocationReq createCollocationReq)
    {
        Collocation collocation =JSON.parseObject(JSON.toJSONString(createCollocationReq),Collocation.class);
        int result = 0;
        try
        {
            result = collocationService.createCollocation(collocation);
        }
        catch (Exception e)
        {
            LOG.error("/collocation/create 创建新的潮搭失败 "+e.toString());
        }
        if(result == 0){
            return WebApiResponse.error("创建潮搭失败");
        }
        return WebApiResponse.success(result);
    }

    @ApiOperation(value = "查询新的潮搭")
    @PostMapping("/query")
    public WebApiResponse<?> queryCollocation(@RequestBody QueryCollocationReq queryCollocationReq)
    {
        PageInfo<Collocation> collocationPageInfo = null;
        try
        {
            collocationPageInfo = collocationService.queryCollocation(queryCollocationReq);
        }
        catch (Exception e)
        {
            LOG.error("/collocation/query 查询新的潮搭失败 "+e.toString());
        }
        if(collocationPageInfo ==null)
        {
            return WebApiResponse.error("查询潮搭失败");
        }
        return WebApiResponse.success(collocationPageInfo);
    }
}
