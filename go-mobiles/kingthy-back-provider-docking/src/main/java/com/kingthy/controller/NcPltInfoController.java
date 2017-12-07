package com.kingthy.controller;

import com.kingthy.dto.NcpltInfoDTO;
import com.kingthy.response.NcpltInfoResp;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.NcPltInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 18:33 on 2017/10/10.
 * @Modified by:
 */
@Api
@RestController
@RequestMapping("/nc/plt")
public class NcPltInfoController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(NcPltInfoController.class);
    public static final String MESSAGE = "服务器出错";

    @Autowired
    private NcPltInfoService ncPltInfoService;

    @ApiOperation(value = "发起请求查询排料后的纸样裁床文件", notes = "")
    @PostMapping("/query")
    public WebApiResponse<?> createNcpltInfo(@RequestBody @ApiParam(name = "NcpltInfoDTO", value = "排料后的文件", required = true) NcpltInfoDTO dto){

        WebApiResponse<?> result = null;

        try {

            result = ncPltInfoService.createNcpltInfo(dto);

        }catch (Exception e){

            LOGGER.error("-------------发起请求查询排料后的纸样裁床文件 失败: "+e+ "-------------");
            return WebApiResponse.error(MESSAGE);
        }

        return result;
    }

    @ApiOperation(value = "查询结果", notes = "")
    @PostMapping("/result")
    public WebApiResponse<?> ncpltInfo(@RequestBody @ApiParam(name = "NcpltInfoDTO", value = "查询结果", required = true) NcpltInfoDTO dto){

        WebApiResponse<?> result = null;

        try {

            result = ncPltInfoService.ncpltInfo(dto);

        }catch (Exception e){
            LOGGER.error("-------------查询结果 失败: "+e+ "-------------");
            return WebApiResponse.error(MESSAGE);
        }

        return result;
    }
}
