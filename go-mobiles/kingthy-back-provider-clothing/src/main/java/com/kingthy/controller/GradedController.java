package com.kingthy.controller;

import com.kingthy.request.CalculatePriceReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.GradedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 描述：GradedController
 * @author  likejie
 * @date 2017/9/20.
 */
@Api
@RestController
@RequestMapping("/graded")
public class GradedController
{
    @Autowired
    private GradedService gradedService;

    @ApiOperation(value = "模拟放码接口", notes = "模拟放码接口")
    @PostMapping("/graded")
    public WebApiResponse<?> getPrice(@RequestBody CalculatePriceReq req)
    {
        try {

            WebApiResponse result = gradedService.gradedCommand(req);
            return result;

        }catch (Exception ex){
            return  WebApiResponse.error(ex.toString());
        }
    }
}
