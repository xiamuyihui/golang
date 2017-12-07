package com.kingthy.service;

import com.github.pagehelper.PageInfo;
import com.kingthy.dto.MomentCommentDto;
import com.kingthy.entity.MomentComment;

/**
 * momentCommentService(描述其作用)
 * <p>
 * @author 赵生辉 2017年05月17日 15:58
 *
 * @version 1.0.0
 */
public interface MomentCommentService
{
    /**
     *
     * ---
     * @param momentComment
     * @return
     */
    int publishComment(MomentComment momentComment);
    /**
     *
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @param momentUuid
     * @param memberUuid
     * @return
     */
    PageInfo<MomentCommentDto> queryMomentCommentPage(Integer pageNo, Integer pageSize, String momentUuid,String memberUuid);
    /**
     *
     * 更新
     * @param uuid
     * @param likes
     * @return
     */
    int update(String uuid, Integer likes);
    /**
     *
     * 删除
     * @param momentComment
     * @return
     */
    int delete(MomentComment momentComment);
}
