package com.kingthy.service;

import com.kingthy.dto.AttentionMemberDTO;
import com.kingthy.dto.FansDTO;
import com.kingthy.request.AttentionReq;
import com.kingthy.response.WebApiResponse;

import java.util.List;

/**
 *
 * 我的关注[业务逻辑处理接口]
 * 
 * @author likejie 2017-5-4
 * 
 * @version 1.0.0
 *
 */
public interface AttentionService
{
    
    /**
     * /** 获取我关注的用户列表
     * 
     * @param memberUuid
     * @return
     */
    List<AttentionMemberDTO> getAttentionList(String memberUuid);
    
    /**
     * 新增
     * 
     * @param attention 我的关注对象
     * @return
     */
    WebApiResponse addAttention(AttentionReq attention);
    
    /**
     * 批量删除我的关注
     * @param memberUuid
     * @param attentionUuids
     * @return
     */
    int batchDeleteAttention(String memberUuid, List<String> attentionUuids);
    
    /**
     * 
     * 获取关注的会员数量
     * @param memberUuid
     * @return
     */
    int getAttentionMemberCount(String memberUuid);
    
    /**
     * 
     * 获取被关注（粉丝）数量
     * @param memberUuid
     * @return
     */
    int getFansCount(String memberUuid);

    /**
     *
     * 获取粉丝列表
     * @param memberUuid
     * @return
     */
    List<FansDTO> getFansLilst(String memberUuid);
}
