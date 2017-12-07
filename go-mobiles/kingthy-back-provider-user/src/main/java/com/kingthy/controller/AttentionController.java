package com.kingthy.controller;

import com.kingthy.cache.RedisManager;
import com.kingthy.common.CommonUtils;
import com.kingthy.dto.AttentionMemberDTO;
import com.kingthy.dto.FansDTO;
import com.kingthy.request.AttentionReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.response.WebApiResponse.ResponseMsg;
import com.kingthy.service.AttentionService;
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
 * 我的关注 [控制器]
 * 
 * @author likejie 2017-5-4
 * 
 * @version 1.0.0
 *
 */
@Api
@RestController
@RequestMapping("/attention")
public class AttentionController 
{
    
    private static final Logger LOG = LoggerFactory.getLogger(AttentionController.class);

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private AttentionService attentionService;;
    
    @ApiOperation(value = "获取我的关注列表", notes = "获取我的关注列表")
    @GetMapping("/getAttentionList")
    public WebApiResponse<?> getAttentionList(@RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token)
    {
        try
        {
            //获取登录用户
            String memberUuid = redisManager.get(token);
            if (StringUtils.isBlank(memberUuid))
            {
                return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
            }
            List<AttentionMemberDTO> list = attentionService.getAttentionList(memberUuid);
            return WebApiResponse.success(list);
        }
        catch (Exception e)
        {
            LOG.error("getAttentionList error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }
    
    @ApiOperation(value = "添加关注", notes = "添加关注")
    @PostMapping("/addAttention")
    public WebApiResponse<?> addAttention(
        @RequestBody @ApiParam(name = "attention", value = "我关注的对象") AttentionReq attention,
        @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token)
    {
        try
        {
            //获取登录用户
            String memberUuid =  redisManager.get(token);
            if (StringUtils.isBlank(memberUuid))
            {
                return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
            }
            if (attention == null)
            {
                return WebApiResponse.error("提交的数据为空");
            }
            if (StringUtils.isBlank(attention.getAttentionUuid()))
            {
                return WebApiResponse.error("参数attentionUuid必填");
            }else {
                if(!StringUtils.isNumeric((attention.getAttentionUuid()))){
                    return WebApiResponse.error("参数attentionUuid格式不正确");
                }
            }
            attention.setMemberUuid(memberUuid);
            return attentionService.addAttention(attention);
            
        }
        catch (Exception e)
        {
            LOG.error("addAttention error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }
    
    @ApiOperation(value = "删除我的关注，支持批量操作", notes = "删除我的关注，支持批量操作")
    @GetMapping("/batchDeleteAttention/{attentionUuids}")
    public WebApiResponse<?> batchDeleteAttention(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
            @PathVariable @ApiParam(name = "attentionUuids", value = "关注的会员Uuid数组") List<String> attentionUuids)
    {
        try
        {
            //获取登录用户
            String memberUuid = redisManager.get(token);
            if (StringUtils.isBlank(memberUuid))
            {
                return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
            }
            if (attentionUuids == null || attentionUuids.isEmpty())
            {
                return WebApiResponse.error(ResponseMsg.PARAMETER_ERROR.getValue());
            }
            int res = attentionService.batchDeleteAttention(memberUuid, attentionUuids);
            if (res > 0)
            {
                return WebApiResponse.success();
            }
            else
            {
                return WebApiResponse.error(ResponseMsg.FAIL.getValue());
            }
            
        }
        catch (Exception e)
        {
            LOG.error("batchDeleteAttention error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }
    
    @ApiOperation(value = "获取我的粉丝数量", notes = "获取我的粉丝数量")
    @GetMapping("/getFansCount")
    public WebApiResponse<?> getFansCount(@RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token)
    {
        try
        {
            // 从缓存获取token
            String memberUuid = redisManager.get(token);
            if (StringUtils.isBlank(memberUuid))
            {
                return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
            }
            int count = attentionService.getFansCount(memberUuid);
            return WebApiResponse.success(count);
            
        }
        catch (Exception e)
        {
            LOG.error("getFansCount error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }
    
    @ApiOperation(value = "获取我的关注数量", notes = "获取我的关注数量")
    @GetMapping("/getAttentionMemberCount")
    public WebApiResponse<?> getAttentionMemberCount(
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
            return WebApiResponse.success(attentionService.getAttentionMemberCount(memberUuid));
        }
        catch (Exception e)
        {
            LOG.error("getAttentionMemberCount error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }
    
    @ApiOperation(value = "获取我的粉丝列表", notes = "获取我的粉丝列表")
    @GetMapping("/getFansLilst")
    public WebApiResponse<?> getFansLilst(@RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token)
    {
        try
        {
            // 从缓存获取token
            String memberUuid = redisManager.get(token);
            if (StringUtils.isBlank(memberUuid))
            {
                return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
            }
            List<FansDTO> list = attentionService.getFansLilst(memberUuid);
            return WebApiResponse.success(list);
            
        }
        catch (Exception e)
        {
            LOG.error("getFansLilst error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }
    
}
