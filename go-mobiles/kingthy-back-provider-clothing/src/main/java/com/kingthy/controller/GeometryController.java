package com.kingthy.controller;

import com.kingthy.cache.RedisManager;
import com.kingthy.request.CalculatePriceReq;
import com.kingthy.request.ModelRenderReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.PriceService;
import com.kingthy.service.RenderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;



/**
 * 描述：几何应用接口
 * @author  likejie
 * @date 2017/9/20.
 */
@Api
@RestController
@RequestMapping("/geometry")
public class GeometryController  {

    @Autowired
    private PriceService priceService;

    @Autowired
    private RenderService renderService;
    @Autowired
    private RedisManager redisManager;

    @ApiOperation(value = "测试shell命令", notes = "测试shell命令")
    @PostMapping("/getPrice")
    public WebApiResponse<?> getPrice(@RequestBody CalculatePriceReq req)
    {
        try {

            WebApiResponse result = priceService.getPrice(req);
            return result;
        }catch (Exception ex){
            return  WebApiResponse.error(ex.toString());
        }
    }
    @ApiOperation(value = "生成人体模型", notes = "生成人体模型")
    @PostMapping("/createModel")
    public WebApiResponse<?>  createModel(@RequestBody ModelRenderReq req){
        try {
            String memberUuid=redisManager.get(req.getToken());
            if(StringUtils.isBlank(memberUuid)){
                return WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
            }
            req.setMemberUuid(memberUuid);
            if(req.getFilelData().length<=0){
                return WebApiResponse.error("参数fileData无效");
            }
            return renderService.modelRender(req);
        }catch (Exception ex){
            return  WebApiResponse.error(ex.toString());
        }
    }
    @ApiOperation(value = "生成人体模型", notes = "生成人体模型")
    @PostMapping("/createModelTest")
    public WebApiResponse<?>  createModelTest(@RequestParam("file") MultipartFile file){
        try {
            ModelRenderReq req=new ModelRenderReq();
            req.setUnzip(false);
            req.setExtName(".zip");
            req.setFilelData(file.getBytes());
            return renderService.modelRender(req);
        }catch (Exception ex){
            return  WebApiResponse.error(ex.toString());
        }
    }
}
