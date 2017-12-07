package com.kingthy.controller;

import com.kingthy.common.CommonUtils;
import com.kingthy.entity.ClassCategory;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.ClassCategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassCategoryController(款式分类控制层)
 * @author zhaochen 2017年9月18日 下午2:24:45
 *
 * @version 1.0.0
 */
@RestController
@RequestMapping(value = "/classCategory")
public class ClassCategoryController extends BaseController{
    private static final Logger LOG = LoggerFactory.getLogger(ClassCategoryController.class);

    @Autowired
    private ClassCategoryService classCategoryService;

    @ApiOperation(value = "查询所有款式")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public WebApiResponse<List<ClassCategory>> findAllNodes(@RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token) {
        List<ClassCategory> ClassCategoryList;
        try {
            String memberUuid = getLoginerByToken(token);
            if (StringUtils.isEmpty(memberUuid)){
                return WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
            }
            ClassCategoryList = classCategoryService.findAllNodes();
        } catch (Exception e) {
            LOG.error("/classCategory/findTopNodes:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
        if (ClassCategoryList != null && ClassCategoryList.size() > 0) {
            return WebApiResponse.success(ClassCategoryList);
        }
        return WebApiResponse.error(WebApiResponse.ResponseMsg.FAIL.getValue());
    }

    @ApiOperation(value = "查询款式分类信息接口", notes = "根据uuid查询分类信息")
    @RequestMapping(value = "/get/{classCategoryUuid}", method = RequestMethod.GET)
    public WebApiResponse<ClassCategory> getClassCategory(
            @PathVariable @ApiParam(name = "classCategoryUuid", value = "节点主键", required = true) String classCategoryUuid
            ,@RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token) {
        ClassCategory classCategory;
        try {
            String memberUuid = getLoginerByToken(token);
            if (StringUtils.isEmpty(memberUuid)){
                return WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
            }
            classCategory = classCategoryService.findClassCategoryByUuid(classCategoryUuid);
        } catch (Exception e) {
            LOG.error("/classCategory/get/{classCategoryUuid}:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
        if (classCategory != null) {
            return WebApiResponse.success(classCategory);
        }
        return WebApiResponse.error(WebApiResponse.ResponseMsg.FAIL.getValue());
    }

}
