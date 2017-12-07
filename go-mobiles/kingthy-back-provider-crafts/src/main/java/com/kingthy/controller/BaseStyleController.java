package com.kingthy.controller;

import com.github.pagehelper.PageInfo;
import com.kingthy.common.CommonUtils;
import com.kingthy.dto.BaseStyleFileDto;
import com.kingthy.entity.BaseStyle;
import com.kingthy.request.BaseStylePageReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.BaseStyleService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name ClothingStyleController
 * @description 款式控制层
 * @create 2017/9/7
 */
@RestController
@RequestMapping("/baseStyle")
public class BaseStyleController extends BaseController{

    @Autowired
    private BaseStyleService baseStyleService;

    private static final Logger LOG = LoggerFactory.getLogger(BaseStyleController.class);


    @ApiOperation(value = "查询款式详情", notes = "查询款式详情")
    @GetMapping("/queryInfo/{uuid}")
    public WebApiResponse queryBaseStyle(@RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,@PathVariable(value = "uuid") String uuid) {
        String memberUuid = getLoginerByToken(token);
        if (StringUtils.isEmpty(memberUuid)){
            return WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
        }
        BaseStyle returnResult;
        try {
            returnResult = baseStyleService.selectByUuid(uuid);
        } catch (Exception e) {
            LOG.info("/baseStyle/queryInfo：" + e.getMessage() + "，异常" + e);
            return WebApiResponse.error(e.getMessage());
        }
        if (returnResult == null) {
            return WebApiResponse.error("查询款式失败");
        }
        return WebApiResponse.success(returnResult);
    }


    @ApiOperation(value = "查询所有款式", notes = "查询所有的款式")
    @GetMapping("/queryAll")
    public WebApiResponse findAllBaseStyle(@RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token) {
        String memberUuid = getLoginerByToken(token);
        if (StringUtils.isEmpty(memberUuid)){
            return WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
        }
        List<BaseStyle> returnResult;
        try {
            returnResult = baseStyleService.selectAll();
        } catch (Exception e) {
            LOG.error("/baseStyle/queryAll，异常信息" + e);
            return WebApiResponse.error(e.getMessage());
        }
        return WebApiResponse.success(returnResult);
    }

    @ApiOperation(value = "分页查询所有款式", notes = "分页查询所有款式")
    @PostMapping("/page")
    public WebApiResponse findBaseStylePage(@RequestBody BaseStylePageReq baseStylePageReq,@RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token) {
        String memberUuid = getLoginerByToken(token);
        if (StringUtils.isEmpty(memberUuid)){
            return WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
        }
        PageInfo<BaseStyle> pageInfo;
        try {
            pageInfo = baseStyleService.queryPage(baseStylePageReq);
        } catch (Exception e) {
            LOG.error("/baseStyle/page/，异常信息" + e);
            return WebApiResponse.error(e.getMessage());
        }
        return WebApiResponse.success(pageInfo);
    }

    @ApiOperation(value = "查询所有款式文件", notes = "查询所有款式文件")
    @GetMapping("/queryAllFiles")
    public WebApiResponse findAllBaseStyleFiles(@RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token) {
        String memberUuid = getLoginerByToken(token);
        if (StringUtils.isEmpty(memberUuid)){
            return WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
        }
        List<BaseStyleFileDto> returnResult;
        try {
            returnResult = baseStyleService.findFiles();
        } catch (Exception e) {
            LOG.error("/baseStyle/queryAllFiles，异常信息" + e);
            return WebApiResponse.error(e.getMessage());
        }
        return WebApiResponse.success(returnResult);
    }
}
