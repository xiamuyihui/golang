package com.kingthy.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.kingthy.cache.RedisManager;
import com.kingthy.common.CommonUtils;
import com.kingthy.dto.MomentCommentDto;
import com.kingthy.entity.MomentComment;
import com.kingthy.request.DeleteCommentReq;
import com.kingthy.request.PublishCommentReq;
import com.kingthy.request.QueryCommentReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.MomentCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * MomentCommentController(描述其作用)
 * <p>
 * @author 赵生辉 2017年05月17日 18:09
 *
 * @version 1.0.0
 */
@Api
@RestController
@RequestMapping("/momentComment")
public class MomentCommentController
{
    private static final Logger LOG = LoggerFactory.getLogger(MomentCommentController.class);

    @Autowired
    private MomentCommentService momentCommentService;

    @Autowired
    RedisManager redisManager;
    @ApiOperation(value = "发表评论")
    @PostMapping("/publish")
    public WebApiResponse<?> publishComment(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
            @RequestBody PublishCommentReq publishCommentReq)
    {
        String memberUuid = redisManager.get(token);
        MomentComment momentComment = JSON.parseObject(JSON.toJSONString(publishCommentReq),MomentComment.class);
        momentComment.setMemberUuid(memberUuid);
        int result = 0;
        try
        {
            result = momentCommentService.publishComment(momentComment);
        }
        catch (Exception e)
        {
            LOG.error("/momentComment/publish:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
        if(result == 0)
        {
            return WebApiResponse.error("发布评论失败");
        }
        return WebApiResponse.success(result);
    }

    @ApiOperation(value = "删除评论")
    @PostMapping("")
    public WebApiResponse<?> deleteComment(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
            @RequestBody DeleteCommentReq deleteCommentReq)
    {
        String memberUuid = redisManager.get(token);
        MomentComment momentComment = JSON.parseObject(JSON.toJSONString(deleteCommentReq),MomentComment.class);
        momentComment.setMemberUuid(memberUuid);
        int result;
        try
        {
            result = momentCommentService.delete(momentComment);
        }
        catch (Exception e)
        {
            LOG.error("/momentComment:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
        return WebApiResponse.success(result);
    }

    @ApiOperation(value = "查询评论")
    @PostMapping("/query")
    public WebApiResponse<?> queryComment(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
            @RequestBody QueryCommentReq queryCommentReq)
    {
        String memberUuid = redisManager.get(token);
        PageInfo<MomentCommentDto> result;
        try
        {
            result = momentCommentService.queryMomentCommentPage(queryCommentReq.getPageNo(),
                queryCommentReq.getPageSize(),queryCommentReq.getMomentUuid(),memberUuid);
        }
        catch (Exception e)
        {
            LOG.error("/momentComment/publish:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
        if(result == null)
        {
            return WebApiResponse.error("查询评论失败");
        }
        return WebApiResponse.success(result);
    }
}
