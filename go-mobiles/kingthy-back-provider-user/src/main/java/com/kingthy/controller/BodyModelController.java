package com.kingthy.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kingthy.cache.RedisManager;
import com.kingthy.common.CommonUtils;
import com.kingthy.entity.Figure;
import com.kingthy.request.CreaterModelReq;
import com.kingthy.request.UpdateModelReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.response.WebApiResponse.ResponseMsg;
import com.kingthy.service.BodyModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

 /**
 *
 * 人体模型 [控制器]
 * 
 * @author likejie 2017-4-21
 * 
 * @version 1.0.0
 *
 */
@Api
@RestController
@RequestMapping("/bodyModel")
public class BodyModelController  {

    private static final Logger LOG = LoggerFactory.getLogger(BodyModelController.class);
    

    
    @Autowired
    private BodyModelService bodyModelService;
    @Autowired
    private RedisManager redisManager;
    @ApiOperation(value = "获取人体模型", notes = "")
    @GetMapping("/getBodyModelList")
    public WebApiResponse<?> getBodyModelList(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token)
    {
        try
        {
            // 从缓存获取token
            String memberUuid = redisManager.get(token);
            if (StringUtils.isBlank(memberUuid))
            {
                return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
            }
            Figure bm=new Figure();
            bm.setMemberUuid(memberUuid);
            List<Figure> list = bodyModelService.getBodyModelList(bm);
            return WebApiResponse.success(list);

        }
        catch (Exception e)
        {
            LOG.error("BodyModelController.getBodyModelList error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }

     @ApiOperation(value = "根据UUID获取人体模型", notes = "")
     @GetMapping("/getBodyModelByUuid/{uuid}")
     public WebApiResponse<?> getBodyModelByUuid(
             @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
             @PathVariable @ApiParam(name = "uuid", value = "业务主键") String uuid)
     {
         try
         {
             // 从缓存获取token
             String memberUuid = redisManager.get(token);
             if (StringUtils.isBlank(memberUuid))
             {
                 return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
             }
             if(StringUtils.isBlank(uuid)){
                 return WebApiResponse.error(ResponseMsg.PARAMETER_ERROR.getValue());
             }
             Figure queryParm=new Figure();
             queryParm.setUuid(uuid);
             List<Figure> list = bodyModelService.getBodyModelList(queryParm);
             if(list!=null && !list.isEmpty()){
                 return WebApiResponse.success(list.get(0));
             }
             return WebApiResponse.success(null);

         }
         catch (Exception e)
         {
             LOG.error("BodyModelController.getBodyModelByUuid error:" + e.toString());
             return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
         }
     }
     @ApiOperation(value = "获取用户默认的人体模型")
     @GetMapping("/getBodyModelByUuid")
     public WebApiResponse<?> getDefaultBodyModel(
             @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token)
     {
         try
         {
             // 从缓存获取token
             String memberUuid = redisManager.get(token);
             if (StringUtils.isBlank(memberUuid))
             {
                 return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
             }
             Figure queryParm=new Figure();
             queryParm.setMemberUuid(memberUuid);
             queryParm.setIsDefault(true);
             List<Figure> list = bodyModelService.getBodyModelList(queryParm);
             if(list!=null&&list.size()>0){
                 return WebApiResponse.success(list.get(0));
             }
             return WebApiResponse.success(null);

         }
         catch (Exception e)
         {
             LOG.error("BodyModelController.getDefaultBodyModel error:" + e.toString());
             return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
         }
     }

     @ApiOperation(value = "创建新的人体模型",notes = "")
     @PostMapping("/create")
     public WebApiResponse<?> createrModel(
             @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
             @RequestBody CreaterModelReq createrModelReq){
         try
         {
             String memberUuid = redisManager.get(token);
             if (StringUtils.isBlank(memberUuid))
             {
                 return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
             }
             Figure bodyModel = JSONObject.parseObject(JSON.toJSONString(createrModelReq),Figure.class);
             bodyModel.setMemberUuid(memberUuid);
             int result = bodyModelService.createBodyModel(bodyModel);
             if(result == 0)
             {
                 return WebApiResponse.error("创建人体模型失败");
             }else{
                 return WebApiResponse.success(result);
             }
         }
         catch (Exception e)
         {
             LOG.error("/bodyModel/create:" + e);
             return WebApiResponse.error(e.getMessage());
         }

     }

     @ApiOperation(value = "更新人体模型",notes = "")
     @PutMapping("/update")
     public WebApiResponse<?> updateModel(@RequestBody UpdateModelReq updateModelReq){

         Figure bodyModel = JSONObject.parseObject(JSON.toJSONString(updateModelReq),Figure.class);
         int result;

         try
         {
             result = bodyModelService.updateBodyModel(bodyModel);
             if(result == 0)
             {
                 return WebApiResponse.error("更新人体模型失败");
             }
         }
         catch (Exception e)
         {
             LOG.error("/bodyModel/update:" + e);
             return WebApiResponse.error(e.getMessage());
         }
         return WebApiResponse.success(result);
     }

     @ApiOperation(value = "删除人体模型",notes = "")
     @PostMapping("/delete/{uuid}")
     public WebApiResponse<?> deleteModel(@PathVariable(name = "uuid") String uuid){

         int result;

         try
         {
             result = bodyModelService.deleteBodyModel(uuid);
             if(result == 0)
             {
                 return WebApiResponse.error("删除人体模型失败");
             }
         }
         catch (Exception e)
         {
             LOG.error("/bodyModel/delete:" + e);
             return WebApiResponse.error(e.getMessage());
         }
         return WebApiResponse.success(result);
     }

}
