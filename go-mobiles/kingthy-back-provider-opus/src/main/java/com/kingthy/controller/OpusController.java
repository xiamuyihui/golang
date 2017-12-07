package com.kingthy.controller;

import com.alibaba.fastjson.JSON;
import com.kingthy.dto.OpusDto;
import com.kingthy.entity.Opus;
import com.kingthy.request.CreateOpusReq;
import com.kingthy.request.DeleteOpusReq;
import com.kingthy.request.QueryOpusListReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.OpusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author shenghuizhao
 */
@Api
@RestController
@RequestMapping("/opus")
public class OpusController
{

    @Autowired
    private OpusService opusService;
    private static final Logger LOG = LoggerFactory.getLogger(OpusController.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    private String getMemberByToken(String token)
    {
        String memberUuid = stringRedisTemplate.opsForValue().get(token);
        return memberUuid;
    }

    /**
     * 仅供自己模拟测试并非新作品创建的入口
     */
    @ApiOperation(value = "创建新的作品",notes = "")
    @PostMapping("/create")
    public WebApiResponse<?> addOpus(@RequestBody @Valid CreateOpusReq createOpusReq,BindingResult bindingResult,
        @RequestHeader("Authorization") String token)
    {
        if(bindingResult.hasErrors())
        {
            return WebApiResponse.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        LOG.info("创建作品的参数为："+ JSON.toJSONString(createOpusReq));
        Opus opus = JSON.parseObject(JSON.toJSONString(createOpusReq),Opus.class);


        String result = opusService.createOpus(opus);
        return WebApiResponse.success(result);

    }

    @ApiOperation(value = "查询作品的列表",notes = "")
    @PostMapping("/queryOpusList")
    public WebApiResponse<?> queryOpusList(@RequestBody @Valid QueryOpusListReq queryOpusListReq,
        @RequestHeader("Authorization") String token){
        LOG.info("查询作品的参数为："+ JSON.toJSONString(queryOpusListReq));
        Opus opus = JSON.parseObject(JSON.toJSONString(queryOpusListReq),Opus.class);
        List<Opus> opusList = null;
        try
        {
            opusList = opusService.queryOpusList(opus);
        }
        catch (Exception e)
        {
            LOG.error("/opus/queryOpusList:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
        if(opusList == null || opusList.size() == 0)
        {
            return WebApiResponse.error("没有找到作品");
        }
        else
        {
            return WebApiResponse.success(opusList);
        }
    }

    @ApiOperation(value = "查询作品的详细信息",notes = "")
    @GetMapping(value = "/{uuid}")
    public WebApiResponse<?> queryOpusInfo(@PathVariable(name = "uuid") String uuid,
        @RequestHeader("Authorization") String token)
    {
        LOG.info("查询作品的详细信息的参数为："+ uuid);
        OpusDto opusDto = null;
        try
        {
            opusDto = opusService.queryOpusInfo(uuid);
        }
        catch (Exception e)
        {
            LOG.error("/opus/queryOpusInfo/{uuid}:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
        return WebApiResponse.success(opusDto);
    }

    @ApiOperation(value = "查询作品的详细信息",notes = "")
    @GetMapping(value = "/query/{uuid}")
    public WebApiResponse<?> queryOpus(@PathVariable(name = "uuid") String uuid,
        @RequestHeader("Authorization") String token)
    {
        String memberUuid = getMemberByToken(token);
        LOG.info("查询作品的详细信息的参数为："+ uuid);
        Opus opus = null;
        try
        {
            opus = opusService.queryOpus(uuid,memberUuid);
        }
        catch (Exception e)
        {
            LOG.error("/opus/queryOpusInfo/{uuid}:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
        return WebApiResponse.success(opus);
    }

    @ApiOperation(value = "删除作品",notes = "")
    @PostMapping(value = "")
    public WebApiResponse<?> deleteOpus(@RequestBody DeleteOpusReq deleteOpusReq,
        @RequestHeader("Authorization") String token)
    {
        String memberUuid = getMemberByToken(token);
        if(deleteOpusReq.getOpusList()==null || deleteOpusReq.getOpusList().size()==0){
            return WebApiResponse.error("作品的业务主键不能全部为空");
        }
        LOG.info("删除作品的详细信息的参数为："+ deleteOpusReq.getOpusList().toString());

        int result = 0;
        try
        {
            result = opusService.delete(deleteOpusReq.getOpusList(),memberUuid);
        }
        catch (Exception e)
        {
            LOG.error("/opus/deleteOpus:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
        return WebApiResponse.success(result);
    }

    @ApiOperation(value="修改作品的状态",notes = "")
    @PutMapping(value="/{uuid}/{status}")
    public WebApiResponse<?> updateOpus(@PathVariable(name="uuid")String uuid ,@PathVariable(name = "status") Integer status,
        @RequestHeader("Authorization") String token)
    {
        String memberUuid = getMemberByToken(token);
        LOG.info("查询作品的详细信息的参数为："+ uuid);
        Opus opus = new Opus();
        opus.setUuid(uuid);
        opus.setOpusStatus(status);
        opus.setMemberUuid(memberUuid);
        int result = 0;
        try
        {
            result = opusService.updateOpus(opus);
        }
        catch (Exception e)
        {
            LOG.error("/opus/updateOpus/{uuid}/{status}:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
        if(result == 0){
            return WebApiResponse.error("修改作品状态失败");
        }
        return WebApiResponse.success(result);
    }

    @ApiOperation(value="查询会员的作品数量",notes = "")
    @GetMapping(value="amount/{uuid}")
    public WebApiResponse<?> opusAmount(@PathVariable(name="uuid")String uuid,
        @RequestHeader("Authorization") String token)
    {
        LOG.info("查询会员的作品数量的参数为："+ uuid);
        int result = 0;
        try
        {
            result = opusService.opusAmount(uuid);
        }
        catch (Exception e)
        {
            LOG.error("/opus/opusAmount/{uuid}:" + e);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
        return WebApiResponse.success(result);
    }
}
