package com.kingthy.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.kingthy.cache.RedisManager;
import com.kingthy.common.CommonUtils;
import com.kingthy.dto.MomentDto;
import com.kingthy.entity.Moments;
import com.kingthy.request.PublishMomentReq;
import com.kingthy.request.QueryMomentPageReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.MomentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * MomentController(动态接口控制层)
 * <p>
 * @author 赵生辉 2017年05月17日 17:56
 *
 * @version 1.0.0
 */
@Api
@RestController
@RequestMapping("/moment")
public class MomentController
{
    private static final Logger LOG = LoggerFactory.getLogger(MomentController.class);

    @Autowired
    private MomentService momentService;

    @Autowired
    RedisManager redisManager;

    @ApiOperation(value = "发布动态")
    @PostMapping("/publish")
    public WebApiResponse<?> publishMoment(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
            @RequestBody @Validated PublishMomentReq publishMomentReq)
    {
        String memberUuid = redisManager.get(token);

        Moments moments = JSON.parseObject(JSON.toJSONString(publishMomentReq),Moments.class);
        moments.setMemberUuid(memberUuid);
        int result = 0;
        try
        {
            result = momentService.publishMoment(moments);
        }
        catch (Exception e)
        {
            LOG.error("/moment/publish:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
        if(result <= 0 )
        {
            return WebApiResponse.error("发布失败");
        }
        else
        {
            return WebApiResponse.success(result);
        }
    }

    @ApiOperation(value = "分页查询动态并排序")
    @PostMapping("/queryPage")
    public WebApiResponse<?> queryMoment(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
            @RequestBody QueryMomentPageReq queryMomentPageReq)
    {

        String memberUuid = redisManager.get(token);
        queryMomentPageReq.setMemberUuid(memberUuid);
        PageInfo<MomentDto> result;
        try
        {
            result = momentService.queryMomentPage(queryMomentPageReq);
        }
        catch (Exception e)
        {
            LOG.error("/moment/queryPage:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
        return WebApiResponse.success(result);
    }

    @ApiOperation(value = "删除动态")
    @PostMapping("/delete/{uuid}")
    public WebApiResponse<?> delete(
            @PathVariable(name = "uuid")String uuid,
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token)
    {
        String memberUuid = redisManager.get(token);
        int result = 0;
        try
        {
            result = momentService.delete(uuid,memberUuid);
        }
        catch (Exception e)
        {
            LOG.error("/moment/delete/{uuid}/{token} "+e.toString());
        }
        if(result == 0){
            return WebApiResponse.error("删除动态失败");
        }
        return WebApiResponse.success(result);
    }

}
