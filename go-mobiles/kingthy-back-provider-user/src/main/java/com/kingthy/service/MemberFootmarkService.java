package com.kingthy.service;

import com.github.pagehelper.PageInfo;
import com.kingthy.dto.MemberFootmarkDTO;
import com.kingthy.dto.SimliarProductDTO;
import com.kingthy.request.MemberFootmarkPageReq;
import com.kingthy.request.MemberFootmarkReq;
import com.kingthy.response.WebApiResponse;

import java.util.List;

/**
 *
 * 我的足迹[业务逻辑处理接口]
 * 
 * @author likejie 2017-4-21
 * 
 * @version 1.0.0
 *
 */
public interface MemberFootmarkService
{
    
    /**
     * 根据条件获取列表
     * @param uuid
     * @return
     */
    List<MemberFootmarkDTO> getMemberFootmarkList(String uuid);
    /**
     * 分页查询会员足迹
     * @param req
     * @return
     */
    PageInfo<MemberFootmarkDTO> pageGetMemberFootmarkList(MemberFootmarkPageReq req);
    /**
     * 新增
     * @param memberFootmark 我的足迹对象
     * @return
     */
    WebApiResponse addMemberFootmark(MemberFootmarkReq memberFootmark);
    
    /**
     *
     * 批量删除我到足迹
     * @param memberUuid
     * @param uuids
     * @return
     */
    int batchDeleteFootmark(String memberUuid,List<String> uuids);
    
    /**
     * 
     * 查找相似商品
     * @param productUuid
     * @return
     */
    List<SimliarProductDTO> getSimilarProduct(String productUuid);
    
}
