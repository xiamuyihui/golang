package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.kingthy.dto.MomentCommentDto;
import com.kingthy.dubbo.review.service.LikesDubboService;
import com.kingthy.dubbo.review.service.MomentCommentDubboService;
import com.kingthy.dubbo.review.service.MomentsDubboService;
import com.kingthy.entity.MomentComment;
import com.kingthy.exception.BizException;
import com.kingthy.service.MomentCommentService;
import com.kingthy.service.MomentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * MomentCommentServiceImpl(描述其作用)
 * <p>
 * @author 赵生辉 2017年05月17日 17:29
 *
 * @version 1.0.0
 */
@Service("momentCommentService")
public class MomentCommentServiceImpl implements MomentCommentService
{


    @Autowired
    private MomentService momentService;


    @Reference(version = "1.0.0",timeout = 3000)
    private MomentsDubboService momentsDubboService;

    @Reference(version = "1.0.0",timeout = 3000)
    private MomentCommentDubboService momentCommentDubboService;

    @Reference(version = "1.0.0",timeout = 3000)
    private LikesDubboService likesDubboService;

    @Override
    public int publishComment(MomentComment momentComment)
    {
        return momentCommentDubboService.publishComment(momentComment);
    }

    @Override
    public PageInfo<MomentCommentDto> queryMomentCommentPage(Integer pageNo, Integer pageSize,String momentUuid,String memberUuid)
    {
        return momentCommentDubboService.selectComment(pageNo,pageSize,momentUuid,memberUuid);
    }

    @Override
    public int update(String uuid, Integer likes)
    {
        int result = momentCommentDubboService.updateLikes(uuid,likes);
        if(result == 0)
        {
            throw new BizException("修改热度失败");
        }
        return result;
    }

    @Override
    public int delete(MomentComment momentComment)
    {
        return momentCommentDubboService.deleteMomentComment(momentComment);
    }
}