package com.kingthy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.kingthy.common.CommonUtils;
import com.kingthy.dto.PartsFileDto;
import com.kingthy.entity.PartsCategory;
import com.kingthy.request.QueryCraftsPartsCategoryReq;
import com.kingthy.request.QueryPartsCategoryReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.PartsCategoryService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PartsCategoryController(部件分类控制层)
 * @author  chenz 2017年9月6日 下午2:03:44
 *
 * @version 1.0.0
 */
@RestController
@RequestMapping("/partsCategory")
public class PartsCategoryController extends BaseController
{

    @Autowired
    private PartsCategoryService partsCategoryService;

    private static final Logger LOG = LoggerFactory.getLogger(PartsCategoryController.class);



    @ApiOperation(value = "查询部件详情", notes = "查询部件详情")
    @GetMapping("/queryInfo/{uuid}")
    public WebApiResponse queryPartsCategory(@PathVariable(value = "uuid")String uuid,@RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token)
    {
        PartsCategory returnResult;
        try
        {
            String memberUuid = getLoginerByToken(token);
            if (StringUtils.isEmpty(memberUuid)){
                return WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
            }
            returnResult = partsCategoryService.findPartsCategory(uuid);
        }
        catch (Exception e)
        {
            LOG.info("/partsCategory/queryInfo：" + e.getMessage() + "，异常" + e);
            return WebApiResponse.error(e.getMessage());
        }
        if (returnResult == null)
        {
            return WebApiResponse.error("查询部件失败");
        }
        return WebApiResponse.success(returnResult);
    }



    @ApiOperation(value = "查询所有部件", notes = "查询所有的部件信息")
    @GetMapping("/queryAll")
    public WebApiResponse findPartsCategory(@RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token)
    {
        List<PartsCategory> returnResult;
        try
        {
            String memberUuid = getLoginerByToken(token);
            if (StringUtils.isEmpty(memberUuid)){
                return WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
            }
            returnResult = partsCategoryService.findAllPartsCategory();
        }
        catch (Exception e)
        {
            LOG.error("/partsCategory/queryAll，异常信息" + e);
            return WebApiResponse.error(e.getMessage());
        }
        return WebApiResponse.success(returnResult);
    }

    @ApiOperation(value = "分页查询所有部件", notes = "通过条件分页查询部件信息")
    @PostMapping("/page")
    public WebApiResponse findPartsCategoryPage(
        @RequestBody QueryCraftsPartsCategoryReq queryCraftsPartsCategoryReq,@RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token)
    {

        PartsCategory partsCategory =
            JSONObject.parseObject(JSON.toJSONString(queryCraftsPartsCategoryReq), PartsCategory.class);
        PageInfo<PartsCategory> pageInfo;
        try
        {
            String memberUuid = getLoginerByToken(token);
            if (StringUtils.isEmpty(memberUuid)){
                return WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
            }
            pageInfo = partsCategoryService.findAllPartsCategoryPage(queryCraftsPartsCategoryReq.getPageNum(),
                    queryCraftsPartsCategoryReq.getPageSize(),
                partsCategory);
        }
        catch (Exception e)
        {
            LOG.error("/partsCategory/page/，异常信息" + e);
            return WebApiResponse.error(e.getMessage());
        }
        return WebApiResponse.success(pageInfo);
    }


    @ApiOperation(value = "查询所有部件的文件", notes = "查询所有的部件的文件信息")
    @GetMapping("/queryAllPartsFiles")
    public WebApiResponse findPartsFiles(@RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token)
    {
        List<PartsFileDto> returnResult;
        try
        {
            String memberUuid = getLoginerByToken(token);
            if (StringUtils.isEmpty(memberUuid)){
                return WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
            }
            returnResult = partsCategoryService.findFiles();
        }
        catch (Exception e)
        {
            LOG.error("/partsCategory/queryAllPartsFiles，异常信息" + e);
            return WebApiResponse.error(e.getMessage());
        }
        return WebApiResponse.success(returnResult);
    }

}
