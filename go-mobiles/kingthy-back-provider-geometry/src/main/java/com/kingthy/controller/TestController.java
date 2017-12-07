package com.kingthy.controller;

import com.kingthy.response.WebApiResponse;
import com.kingthy.service.ITest;
import com.kingthy.service.impl.TestServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;

/**
 * Created by likejie on 2017/9/5.
 */
@Api
@RestController
@RequestMapping("/test")
public class TestController {


    @Autowired
    private ITest service;

    @ApiOperation(value = "测试shell命令", notes = "测试shell命令")
    @GetMapping("/testCommand")
    public WebApiResponse<?> testCommand()
    {
        try {

            String result = service.testCommand("");
            return WebApiResponse.success(result);

        }catch (Exception ex){
            return  WebApiResponse.error(ex.toString());
        }
    }

}
