package com.kingthy.controller;

import com.kingthy.response.EncryptUploadDTO;
import com.kingthy.entity.ModelImage;
import com.kingthy.exception.BizException;
import com.kingthy.response.CreaterModelRes;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.ModelService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author shenghuizhao
 */
@RestController
@RequestMapping("/model")
public class ModelController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelController.class);

    @Autowired
    private ModelService modelService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation(value = "上传生成模型的图片")
    @PostMapping("/create")
    public WebApiResponse<?> uploadModelImage(@RequestBody ModelImage modelImage){

        int result = modelService.sendImage(modelImage);
        return WebApiResponse.success(result);
    }

    private String modelFilePath = "/data/model/";

    /**
     * 二进制流上传文件
     * @param multipartFile
     * @return
     */
    @RequestMapping(value = "/stream", method = RequestMethod.POST)
    public WebApiResponse<?> uploadStream(@RequestParam("file") @ApiParam(name = "file", value = "上传对象", required = true)  MultipartFile multipartFile,
    @RequestHeader(value = "Authorization", required = false) String token){

        String memberUuid = stringRedisTemplate.opsForValue().get(token);
        if (StringUtils.isEmpty(memberUuid)){
            return WebApiResponse.error("token不合法");
        }
        CreaterModelRes resutl = null;
        try{
            resutl = modelService.createrModel(multipartFile,memberUuid);

        }catch(BizException be){
            LOGGER.error("ModelController uploadStream 方法" + be.getMessage());
            be.printStackTrace();
            return WebApiResponse.error("创建失败" + be);
        }
        catch (Exception e){
            LOGGER.error("ModelController uploadStream 方法" + e.getMessage());
            e.printStackTrace();
            return WebApiResponse.error("二进制流上传文件" + e);
        }

        return WebApiResponse.success(resutl);
    }
}
