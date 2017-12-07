package com.kingthy.service;

import com.github.pagehelper.PageInfo;
import com.kingthy.dto.MomentDto;
import com.kingthy.entity.Moments;
import com.kingthy.request.QueryMomentPageReq;

/**
 * momentService(描述其作用)
 * <p>
 * @author 赵生辉 2017年05月17日 15:53
 *
 * @version 1.0.0
 */
public interface MomentService
{
    /**
     * 发布动态
     * @param moments
     * @return
     */
    int publishMoment(Moments moments);

    /**
     * 分页查询动态
     * @param queryMomentPageReq
     * @return
     */
    PageInfo<MomentDto> queryMomentPage(QueryMomentPageReq queryMomentPageReq);

    /**
     * 更新动态
     * @param uuid
     * @param likes
     * @param comments
     * @return
     */
    int update(String uuid, Integer likes,Integer comments);

    /**
     * 删除动态
     * @param uuid
     * @param memberUuid
     * @return
     */
    int delete(String uuid,String memberUuid);
}
