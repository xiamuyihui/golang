package com.kingthy.controller;

import com.kingthy.entity.ModelImage;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.ModelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ModelController(描述其作用)
 * <p>
 * 赵生辉 2017年06月13日 14:01
 *
 * @version 1.0.0
 */
@RestController
@RequestMapping("/model")
public class ModelController
{
    @Autowired
    private ModelService modelService;

    @ApiOperation(value = "上传生成模型的图片")
    @PostMapping
    public WebApiResponse<?> uploadModelImage(@RequestBody ModelImage modelImage){

        WebApiResponse<?> webApiResponse = modelService.uploadModelImage(modelImage);
        return webApiResponse;
    }
}
