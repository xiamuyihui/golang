package com.kingthy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.kingthy.common.CommonUtils;
import com.kingthy.dto.AccessoriesDto;
import com.kingthy.dto.AccessoriesFileDto;
import com.kingthy.entity.Accessories;
import com.kingthy.request.FindPage;
import com.kingthy.request.FindPageCrafts;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.AccessoriesService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhaochen
 */
@RestController
@RequestMapping(value = "accessories")
public class AccessoriesController extends BaseController{
    @Autowired
    private AccessoriesService accessoriesService;

    private static final Logger LOG = LoggerFactory.getLogger(AccessoriesController.class);


    @ApiOperation(value = "查询辅料详情", notes = "查询辅料详情")
    @RequestMapping(value = "/{accessoriesUuid}", method = RequestMethod.GET)
    public WebApiResponse findAccessories(@PathVariable String accessoriesUuid,@RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token) {
        Accessories accessories;
        try {
            String memberUuid = getLoginerByToken(token);
            if (StringUtils.isEmpty(memberUuid)){
                return WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
            }
            accessories = accessoriesService.findAccessories(accessoriesUuid);
        } catch (Exception e) {
            LOG.error("查询辅料详情出错，异常信息" + e);
            return WebApiResponse.error(e.getMessage());
        }
        if (accessories == null) {
            return WebApiResponse.error(WebApiResponse.ResponseMsg.FAIL.getValue());
        }
        return WebApiResponse.success(accessories);
    }

    @ApiOperation(value = "查询辅料列表", notes = "查询辅料列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public WebApiResponse findAccessoriesPage(@RequestBody FindPageCrafts findPageCrafts,@RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token) {
        Accessories accessories = JSONObject.parseObject(JSON.toJSONString(findPageCrafts), Accessories.class);
        PageInfo<AccessoriesDto> accessoriesPage;
        try {
            String memberUuid = getLoginerByToken(token);
            if (StringUtils.isEmpty(memberUuid)){
                return WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
            }
            accessoriesPage = accessoriesService.findAccessoriesPage(findPageCrafts.getPageNum(), findPageCrafts.getPageSize(), accessories);
        } catch (Exception e) {
            LOG.error("查询辅料列表出错，异常信息" + e);
            return WebApiResponse.error(e.getMessage());
        }
        if (accessoriesPage == null) {
            return WebApiResponse.error(WebApiResponse.ResponseMsg.FAIL.getValue());
        }
        return WebApiResponse.success(accessoriesPage);
    }


    @ApiOperation(value = "查询辅料文件", notes = "查询辅料文件")
    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public WebApiResponse findAccessoriesPage(@RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token) {
        List<AccessoriesFileDto> result;
        try {
            String memberUuid = getLoginerByToken(token);
            if (StringUtils.isEmpty(memberUuid)){
                return WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
            }
            result = accessoriesService.findFiles();
        } catch (Exception e) {
            LOG.error("查询辅料文件出错，异常信息" + e);
            return WebApiResponse.error(e.getMessage());
        }
        if (result == null) {
            return WebApiResponse.error(WebApiResponse.ResponseMsg.FAIL.getValue());
        }
        return WebApiResponse.success(result);
    }

}