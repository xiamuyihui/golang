package com.kingthy.controller;

import com.github.pagehelper.PageInfo;
import com.kingthy.cache.RedisManager;
import com.kingthy.common.CommonUtils;
import com.kingthy.entity.ShowVideos;
import com.kingthy.request.ShowVideosReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.response.WebApiResponse.ResponseMsg;
import com.kingthy.service.ShowVideosService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 走秀视频
 * @author  likejie on 2017/9/11.
 */
@Api
@RestController
@RequestMapping("/showvideos")
public class ShowVideosController  {

    private static final Logger logger = LoggerFactory.getLogger(ShowVideosController.class);

    @Autowired
    private ShowVideosService showVideosService;

    @Autowired
    private RedisManager redisManager;

    @ApiOperation(value = "添加走秀视频文件", notes = "添加走秀视频文件")
    @PostMapping("/addShowVideos")
    public WebApiResponse<?> addShowVideos(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
            @RequestBody ShowVideosReq req)
    {
        try
        {
            // 从缓存获取token
            String memberUuid = redisManager.get(token);
            if (StringUtils.isBlank(memberUuid))
            {
                return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
            }
            req.setMemberUuid(memberUuid);
            int result = showVideosService.addShowVideos(req);
            if(result>0){
                return WebApiResponse.success();
            }else{
                return WebApiResponse.error(ResponseMsg.FAIL.getValue());
            }

        }
        catch (Exception e)
        {
            logger.error("ShowVideosController.addShowVideos error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }

    @ApiOperation(value = "删除走秀视频文件", notes = "删除走秀视频文件")
    @DeleteMapping("/deleteShowVideos/{uuid}")
    public WebApiResponse<?> deleteShowVideos( @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
                                              @PathVariable String uuid)
    {
        try
        {
            // 从缓存获取token
            String memberUuid = redisManager.get(token);
            if (StringUtils.isBlank(memberUuid))
            {
                return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
            }
            int result = showVideosService.deleteShowVideos(uuid);
            if(result>0){
                return WebApiResponse.success();
            }else{
                return WebApiResponse.error(ResponseMsg.FAIL.getValue());
            }

        }
        catch (Exception e)
        {
            logger.error("ShowVideosController.deleteShowVideos error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }
    @ApiOperation(value = "分页查询走秀视频列表", notes = "分页查询走秀视频列表")
    @PostMapping("/queryPage")
    public WebApiResponse<?> queryPage(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
            @RequestBody ShowVideosReq req)
    {
        try
        {
            // 从缓存获取token
            String memberUuid = redisManager.get(token);
            if (StringUtils.isBlank(memberUuid))
            {
                return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
            }
            req.setMemberUuid(memberUuid);
            PageInfo<ShowVideos> pageInfo= showVideosService.queryPage(req);
            return WebApiResponse.success(pageInfo);

        }
        catch (Exception e)
        {
            logger.error("ShowVideosController.queryPage error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }
}
