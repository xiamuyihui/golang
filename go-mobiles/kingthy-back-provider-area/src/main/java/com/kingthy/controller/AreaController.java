package com.kingthy.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.kingthy.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.kingthy.entity.Area;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.AreaService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author pany created
 */
@io.swagger.annotations.Api
@RestController
@RequestMapping("/area")
public class AreaController
{
    @Autowired
    AreaService areaService;

    private static final String REGEX = "^\\d+$|-\\d+$";
    
    @ApiOperation(value = "获取省级别的接口", notes = "")
    @GetMapping("/province")
    public WebApiResponse<?> queryProvince()
    {
        java.util.List<Area> areas = areaService.getProvince();
        return WebApiResponse.success(areas);
    }
    
    @ApiOperation(value = "获取市、县级别的接口", notes = "")
    @GetMapping(value = "/{grade_str}/{parentId_str}")
    public WebApiResponse<?> queryCityOrDistrict(
        @PathVariable @ApiParam(name = "grade_str", value = "1:市级,2:区级", required = true) String gradeStr,
        @PathVariable @ApiParam(name = "parentId_str", value = "父级ID", required = true) String parentIdStr,
        @RequestHeader("Authorization") String token)
    {
        Pattern pattern = Pattern.compile(REGEX);
        if (null != gradeStr && null != parentIdStr)
        {
            Matcher isNumGrade = pattern.matcher(gradeStr);
            Matcher isNumParentId = pattern.matcher(parentIdStr);
            if (!isNumGrade.matches() || !isNumParentId.matches())
            {
                return WebApiResponse.error("参数必须是正整数");
            }
            else
            {
                java.util.List<Area> areas =
                    areaService.getCity(Integer.valueOf(gradeStr), Integer.valueOf(parentIdStr));
                return WebApiResponse.success(areas);
            }
        }
        else
        {
            return WebApiResponse.error("参数不能为空");
        }
        
    }
}
