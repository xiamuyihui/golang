package com.kingthy.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kingthy.response.WebApiResponse;
import com.kingthy.service.TupuCheckService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

/**
 * BaseDataController(描述其作用)
 * <p>
 * 赵生辉 2017年05月11日 9:55
 *
 * @version 1.0.0
 */
@Api
@RestController
@RequestMapping("tupuCheck")
public class TupuCheckController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(TupuCheckController.class);
    
    @Autowired
    private TupuCheckService tupuCheckService;
    
    @ApiOperation("检查图片是否非法")
    @PostMapping("")
    public WebApiResponse<?> CheckImage(@RequestBody List<String> imageList)
    {
        JSONObject resut = null;
        try
        {
            resut = tupuCheckService.checkImager(imageList);
        }
        catch (Exception e)
        {
            LOGGER.error("/tupuCheck:" + e);
            return WebApiResponse.error("检测失败");
        }
        if (resut != null)
        {
            return WebApiResponse.success(resut);
        }
        else
        {
            return WebApiResponse.error("检测失败");
        }
    }

    @ApiOperation("检查图片是否非法")
    @PostMapping("/url")
    public WebApiResponse<?> CheckImageUrl(@RequestBody List<String> imageList)
    {
        JSONObject resut = null;
        try
        {
            resut = tupuCheckService.checkImagerUrl(imageList);
        }
        catch (Exception e)
        {
            LOGGER.error("/tupuCheck/url:" + e);
            return WebApiResponse.error("检测失败");
        }
        if (resut != null)
        {
            return WebApiResponse.success(resut);
        }
        else
        {
            return WebApiResponse.error("检测失败");
        }
    }
    
}
