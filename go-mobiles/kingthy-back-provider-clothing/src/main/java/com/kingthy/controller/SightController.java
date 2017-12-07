package com.kingthy.controller;


import com.kingthy.request.ShowVideosReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.RenderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 描述：视觉应用接口
 * @author  likejie
 * @date 2017/9/20.
 */
@Api
@RestController
@RequestMapping("/sight")
public class SightController  {


    @Autowired
    private RenderService renderService;

    @ApiOperation(value = "生成走秀视频", notes = "生成走秀视频")
    @PostMapping("/createVideo")
    public WebApiResponse<?> createVideo(@RequestBody  ShowVideosReq req)
    {
        try {

            WebApiResponse result = renderService.createVideo(req);
            return WebApiResponse.success();

        }catch (Exception ex){
            return  WebApiResponse.error(ex.toString());
        }
    }
}
