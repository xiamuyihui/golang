package com.jxkj.cloud.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jxkj.cloud.service.TbSnService;
import com.kingthy.response.WebApiResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api
@RestController()
@RequestMapping("gererateSn")
public class GenerateSnController
{
    @Autowired
    private TbSnService TbSnService;
    
    @ApiOperation(value = "生成序列号", notes = "")
    @GetMapping(value = "/getSnByType/{type}")
    public WebApiResponse<String> generateSn(
        @PathVariable(name = "type") @ApiParam(name = "type", value = "类型号", required = true) String type)
    {
        // TbSn.Type types = TbSn.Type.valueOf(category);
        if (StringUtils.isNotBlank(type))
        {
            String sn = TbSnService.generateSn(type);
            if ("error".equals(sn))
            {
                return new WebApiResponse<String>().error("未定义的类型");
            }
            return new WebApiResponse<String>().success(sn);
        }
        else
        {
            return new WebApiResponse<String>().error("type is null");
        }
        
    }
    
}
