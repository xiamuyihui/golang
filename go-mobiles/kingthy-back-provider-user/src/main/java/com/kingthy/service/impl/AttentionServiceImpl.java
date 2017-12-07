package com.kingthy.service.impl;


import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.dto.AttentionMemberDTO;
import com.kingthy.dto.FansDTO;
import com.kingthy.dubbo.user.service.AttentionDubboService;
import com.kingthy.entity.Attention;
import com.kingthy.request.AttentionReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.AttentionService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
/**
 *
 * 我的关注 [业务处理实现类]
 * @author likejie 2017-5-4
 * @version 1.0.0
 */
@Service("attentionService")
public class AttentionServiceImpl implements AttentionService
{

    private static final Logger LOGGER= LoggerFactory.getLogger(AttentionServiceImpl.class);
    //创建dubbo服务接口实例
    @Reference(version = "1.0.0")
    private AttentionDubboService attentionDubboService;



    @HystrixCommand(fallbackMethod = "getAttentionListFallback")
    @Override
    public List<AttentionMemberDTO> getAttentionList(String memberUuid)
    {
        return attentionDubboService.getAttentionMemberList(memberUuid);
    }
    @HystrixCommand(fallbackMethod = "addAttentionFallback")
    @Override
    public WebApiResponse addAttention(AttentionReq req)
    {
        String memberUuid=req.getMemberUuid();
        if (memberUuid.equals(req.getAttentionUuid()))
        {
            return WebApiResponse.error("不能关注自己");
        }
        int count = attentionDubboService.getAttentionCount(memberUuid, req.getAttentionUuid());

        if (count > 0)
        {
            return WebApiResponse.error("该用户已关注");
        }
        Date now = new Date();
        Attention attention=new Attention();
        attention.setAttentionUuid(req.getAttentionUuid());
        attention.setMemberUuid(req.getMemberUuid());
        attention.setCreateDate(now);
        attention.setModifyDate(now);
        attention.setVersion(0);
        int res= attentionDubboService.insert(attention);
        if(res==1){
            return WebApiResponse.success();
        }else{
            return WebApiResponse.error(WebApiResponse.ResponseMsg.FAIL.getValue());
        }
    }

    /**
     *
     *
     * 批量删除我的关注
     */
    @HystrixCommand(fallbackMethod = "batchDeleteAttentionFallback")
    @Override
    public int batchDeleteAttention(String memberUuid, List<String> attentionUuids)
    {
        return attentionDubboService.batchDeleteAttention(memberUuid, attentionUuids);
    }
    @HystrixCommand(fallbackMethod = "getAttentionMemberCountFallback")
    @Override
    public int getAttentionMemberCount(String memberUuid)
    {
        return attentionDubboService.getAttentionCount(memberUuid, "");
    }
    @HystrixCommand(fallbackMethod = "getFansCountFallback")
    @Override
    public int getFansCount(String memberUuid)
    {
        return attentionDubboService.getAttentionCount("", memberUuid);
    }

    @HystrixCommand(fallbackMethod = "getFansLilstFallback")
    @Override
    public List<FansDTO> getFansLilst(String memberUuid)
    {
        return attentionDubboService.getFansLilst(memberUuid);
    }

    /**Hystrix 熔断处理  start****/
    private List<AttentionMemberDTO> getAttentionListFallback(String memberUuid,Throwable e){

        LOGGER.error("getAttentionMemberList 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e.toString());
    }
    private List<FansDTO> getFansLilstFallback(String memberUuid ,Throwable e){

        LOGGER.error("getFansLilst 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }
    private WebApiResponse addAttentionFallback(AttentionReq attention,Throwable e) {
        LOGGER.error("addAttention 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }
    private int getFansCountFallback(String memberUuid,Throwable e)
    {
        LOGGER.error("getFansCount 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }
    private int getAttentionMemberCountFallback(String memberUuid,Throwable e){
        LOGGER.error("getAttentionMemberCount 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }
    private int batchDeleteAttentionFallback(String memberUuid, List<String> attentionUuids,Throwable e){
        LOGGER.error("batchDeleteAttention 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }
    /**Hystrix 熔断处理 end****/
}
