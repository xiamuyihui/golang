package com.kingthy.controller;

import com.alibaba.fastjson.JSON;
import com.kingthy.cache.RedisManager;
import com.kingthy.common.CommonUtils;
import com.kingthy.entity.Likes;
import com.kingthy.request.CreateLikeReq;
import com.kingthy.request.DeleteLikeReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.LikesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * LikesController(描述其作用)
 * <p>
 * @author 赵生辉 2017年05月17日 18:10
 *
 * @version 1.0.0
 */
@Api
@RestController
@RequestMapping("like")
public class LikesController
{
    private static final Logger LOG = LoggerFactory.getLogger(LikesController.class);

    @Autowired
    private LikesService likesService;

    @Autowired
    private RedisManager redisManager;

    @ApiOperation(value = "点赞")
    @PostMapping("/create")
    public WebApiResponse<?> createLike(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
            @RequestBody CreateLikeReq createLikeReq) {

        try {
            String memberUuid = redisManager.get(token);
            if (StringUtils.isBlank(memberUuid)) {
                return WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
            }
            Likes likes = JSON.parseObject(JSON.toJSONString(createLikeReq), Likes.class);
            likes.setMemberUuid(memberUuid);
            int result = likesService.createLike(likes);
            if (result == 0) {
                return WebApiResponse.error("点赞失败");
            }
            return WebApiResponse.success(result);
        } catch (Exception e) {
            LOG.error("/like/create:" + e);
            return WebApiResponse.error(e.getMessage());
        }

    }

    @ApiOperation(value = "取消点赞")
    @PostMapping("/delete")
    public WebApiResponse<?> deleteLike(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
            @RequestBody DeleteLikeReq deleteLikeReq)
    {
        String memberUuid = redisManager.get(token);
        if (StringUtils.isBlank(memberUuid)) {
            return WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
        }
        Likes likes = JSON.parseObject(JSON.toJSONString(deleteLikeReq),Likes.class);
        likes.setMemberUuid(memberUuid);
        int result;
        try
        {
            result = likesService.deleteLike(likes);
        }
        catch (Exception e)
        {
            LOG.error("/like/create:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
        if(result == 0)
        {
            return WebApiResponse.error("点赞失败");
        }
        return WebApiResponse.success(result);
    }


}
