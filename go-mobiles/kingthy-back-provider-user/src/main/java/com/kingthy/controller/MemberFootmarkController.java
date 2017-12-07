package com.kingthy.controller;

import com.github.pagehelper.PageInfo;
import com.kingthy.cache.RedisManager;
import com.kingthy.common.CommonUtils;
import com.kingthy.dto.MemberFootmarkDTO;
import com.kingthy.dto.SimliarProductDTO;
import com.kingthy.request.MemberFootmarkPageReq;
import com.kingthy.request.MemberFootmarkReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.response.WebApiResponse.ResponseMsg;
import com.kingthy.service.MemberFootmarkService;
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
 * 我的足迹 [控制器]
 * 
 * @author likejie 2017-4-21
 * 
 * @version 1.0.0
 *
 */
@Api
@RestController
@RequestMapping("/memberFootmark")
public class MemberFootmarkController 
{
    
    private static final Logger LOG = LoggerFactory.getLogger(MemberFootmarkController.class);
    
    @Autowired
    private MemberFootmarkService memberFootmarkService;

    @Autowired
    private RedisManager redisManager;
    
    @ApiOperation(value = "获取我的足迹")
    @GetMapping("/getMemberFootmarkList")
    public WebApiResponse<?> getMemberFootmarkList(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token)
    {
        try
        {
            String memberUuid = redisManager.get(token);
            if (StringUtils.isNotBlank(memberUuid))
            {
                List<MemberFootmarkDTO> list = memberFootmarkService.getMemberFootmarkList(memberUuid);
                return WebApiResponse.success(list);
            }
            else
            {
                return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
            }
            
        }
        catch (Exception e)
        {
            LOG.error("MemberFootmarkController.getMemberFootmarkList error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }
    @ApiOperation(value = "分页查询我的足迹", notes = "分页查询我的足迹")
    @PostMapping("/pageGetMemberFootmarkList")
    public WebApiResponse<?> pageGetMemberFootmarkList(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
            @RequestBody MemberFootmarkPageReq req)
    {
        try
        {
            String memberUuid = redisManager.get(token);
            if (StringUtils.isNotBlank(memberUuid))
            {
                req.setMemberUuid(memberUuid);
                PageInfo<MemberFootmarkDTO> pageT= memberFootmarkService.pageGetMemberFootmarkList(req);
                return WebApiResponse.success(pageT);
            }
            else
            {
                return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
            }

        }
        catch (Exception e)
        {
            LOG.error("MemberFootmarkController.pageGetMemberFootmarkList error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }

    @ApiOperation(value = "删除我的足迹")
    @GetMapping("/deleteMemberFootmark/{uuids}")
    public WebApiResponse<?> deleteMemberFootmark(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
            @PathVariable @ApiParam(name = "uuids", value = "业务编号集合:1,2,3,4....", required = true) List<String> uuids) {
        try {
            String memberUuid = redisManager.get(token);
            if (uuids == null) {
                return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
            }
            if (uuids.isEmpty()) {
                return WebApiResponse.error("业务编号不能为空");
            }
            int res = memberFootmarkService.batchDeleteFootmark(memberUuid, uuids);
            if (res > 0) {
                return WebApiResponse.success();
            } else {
                return WebApiResponse.error(ResponseMsg.FAIL.getValue());
            }

        } catch (Exception e) {
            LOG.error("MemberFootmarkController.deleteMemberFootmark error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }
    
    @ApiOperation(value = "新增我的足迹", notes = "新增我的足迹")
    @PostMapping("/addMemberFootmark")
    public WebApiResponse<?> addMemberFootmark(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
            @RequestBody @ApiParam(name = "memberFootmark", value = "我的足迹对象") MemberFootmarkReq req)
    {
        try
        {
            if (req == null)
            {
                return WebApiResponse.error(ResponseMsg.PARAMETER_ERROR.getValue());
            }
            String memberUuid = redisManager.get(token);
            
            if (StringUtils.isBlank(memberUuid))
            {
                return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
            }
            if (StringUtils.isBlank(req.getProductUuid()))
            {
                return WebApiResponse.error("商品编号不能为空");
            }
            req.setMemberUuid(memberUuid);

            return memberFootmarkService.addMemberFootmark(req);
        }
        catch (Exception e)
        {
            LOG.error("MemberFootmarkController.addMemberFootmark error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }
    
    @ApiOperation(value = "查找相似商品")
    @GetMapping("/getSimilarProduct/{productUuid}")
    public WebApiResponse<?> getSimilarProduct(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
        @PathVariable @ApiParam(name = "productUuid", value = "商品业务编号", required = true) String productUuid)
    {
        try
        {
            String memberUuid = redisManager.get(token);
            if (StringUtils.isBlank(memberUuid))
            {
                return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
            }
            if (StringUtils.isBlank(productUuid))
            {
                return WebApiResponse.error("商品编号为空");
            }
            List<SimliarProductDTO> list = memberFootmarkService.getSimilarProduct(productUuid);
            return WebApiResponse.success(list);
            
        }
        catch (Exception e)
        {
            LOG.error("MemberFootmarkController.getSimilarProduct error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }
}
