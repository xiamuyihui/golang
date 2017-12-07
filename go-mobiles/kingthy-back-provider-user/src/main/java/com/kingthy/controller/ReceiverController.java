package com.kingthy.controller;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.kingthy.cache.RedisManager;
import com.kingthy.common.CommonUtils;
import com.kingthy.request.ReceiverReq;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.kingthy.dto.MemberReceiverDTO;
import com.kingthy.entity.Receiver;
import com.kingthy.response.WebApiResponse;
import com.kingthy.response.WebApiResponse.ResponseMsg;
import com.kingthy.service.ReceiverService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 *
 * 会员收获地址 [控制器]
 * 
 * @author likejie 2017-4-20
 * 
 * @version 1.0.0
 *
 */
@Api
@RestController
@RequestMapping("/receiver")
public class ReceiverController 
{
    
    private static final Logger LOG = LoggerFactory.getLogger(ReceiverController.class);
    
    @Autowired
    private ReceiverService receiverService;
    @Autowired
    private RedisManager redisManager;
    
    @ApiOperation(value = "获取会员收获地址")
    @GetMapping("/getReceiverList")
    public WebApiResponse<?> getReceiverList(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token)
    {
        try
        {
            String memberUuid = redisManager.get(token);
            if (StringUtils.isNotBlank(memberUuid))
            {
                List<MemberReceiverDTO> list  = receiverService.getMemberReceiverList(memberUuid);
                return WebApiResponse.success(list);
            }
            else
            {
                return WebApiResponse.error("获取用户信息失败");
            }
            
        }
        catch (Exception e)
        {
            LOG.error("ReceiverController.getReceiverList error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }
    
    @ApiOperation(value = "新增会员收获地址")
    @PostMapping("/addReceiver")
    public WebApiResponse<?> addReceiver(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
        @RequestBody @ApiParam(name = "receiver", value = "会员收获地址对象") Receiver receiver)
    {
        try
        {
            
            String memberUuid = redisManager.get(token);
            if (StringUtils.isNotBlank(memberUuid))
            {
                receiver.setMemberUuid(memberUuid);
                int res = receiverService.addReceiver(receiver);
                if (res > 0)
                {
                    return WebApiResponse.success("success");
                }
                else
                {
                    return WebApiResponse.error("操作失败");
                }
            }
            else
            {
                return WebApiResponse.error("获取用户信息失败");
            }
            
        }
        catch (Exception e)
        {
            LOG.error("ReceiverController.addReceiver error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }
    
    @ApiOperation(value = "根据业务编号删除会员收获地址")
    @GetMapping("/deleteReceiver/{uuid}")
    public WebApiResponse<?> deleteReceiver(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
        @PathVariable @ApiParam(name = "uuid", value = "收获地址的业务主键", required = true) String uuid)
    {
        try
        {
            String memberUuid = redisManager.get(token);
            if (StringUtils.isBlank(memberUuid))
            {
                return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
            }
            if (StringUtils.isBlank(uuid))
            {
                return WebApiResponse.error("uuid参数为空");
            }

            int res = receiverService.deleteReceiver(uuid,memberUuid);
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
            LOG.error("ReceiverController.deleteReceiver error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }

    @ApiOperation(value = "分页查询收获地址", notes = "分页查询收获地址")
    @PostMapping("/pageGetReceiverList")
    public  WebApiResponse<?> pageGetReceiverList(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
            @RequestBody ReceiverReq req){

        try{
            String memberUuid = redisManager.get(token);
            if (StringUtils.isBlank(memberUuid))
            {
                return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
            }
            req.setMemberUuid(memberUuid);
            PageInfo<Receiver> pageInfo=receiverService.pageGetReceiverList(req);
            return WebApiResponse.success(pageInfo);
        }catch (Exception ex){
            LOG.error("pageGetReceiverList error:" + ex.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }

    @ApiOperation(value = "更新会员收获地址", notes = "")
    @PostMapping("/updateReceiver")
    public WebApiResponse<?> updateReceiver(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
        @RequestBody @ApiParam(name = "receiver", value = "会员收获地址对象") Receiver receiver)
    {
        try
        {
            String memberUuid = redisManager.get(token);
            if (StringUtils.isBlank(memberUuid))
            {
                return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
            }
            if (StringUtils.isBlank(receiver.getUuid()))
            {
                return WebApiResponse.error("uuid参数为空");
            }
            // 更新
            receiver.setMemberUuid(memberUuid);
            int res = receiverService.updateReceiver(receiver);
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
            LOG.error("ReceiverController.updateReceiver error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }
    
    @ApiOperation(value = "设置默认的收获地址", notes = "")
    @GetMapping("/setDefaultReceiver/{uuid}")
    public WebApiResponse<?> setDefaultReceiver(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
        @PathVariable @ApiParam(name = "uuid", value = "收获地址的业务主键", required = true) String uuid)
    {
        try
        {
            String memberUuid = redisManager.get(token);
            if (StringUtils.isBlank(memberUuid))
            {
                return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
            }
            if (StringUtils.isBlank(uuid))
            {
                return WebApiResponse.error("地址uuid参数为空");
            }
            // 更新
            int res = receiverService.setDefaultReceiver(memberUuid, uuid);
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
            LOG.error("ReceiverController.setDefaultReceiver error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }
    
    @ApiOperation(value = "获取会员默认的收获地址", notes = "")
    @GetMapping("/getDefaultReceiver")
    public WebApiResponse<?> getDefaultReceiver(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token)
    {
        try
        {
            String memberUuid = redisManager.get(token);
            if (StringUtils.isBlank(memberUuid))
            {
                return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
            }
            Receiver receiver = receiverService.getDefaultReceiver(memberUuid);
            return WebApiResponse.success(receiver);
            
        }
        catch (Exception e)
        {
            LOG.error("ReceiverController.getDefaultReceiver error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }
}
