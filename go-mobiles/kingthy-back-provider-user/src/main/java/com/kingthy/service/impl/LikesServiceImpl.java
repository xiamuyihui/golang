package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.dubbo.review.service.LikesDubboService;
import com.kingthy.dubbo.review.service.MomentCommentDubboService;
import com.kingthy.dubbo.review.service.MomentsDubboService;
import com.kingthy.entity.Likes;
import com.kingthy.exception.BizException;
import com.kingthy.service.LikesService;
import com.kingthy.service.MomentCommentService;
import com.kingthy.service.MomentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;

/**
 * LikesServiceImpl(描述其作用)
 * <p>
 * @author 赵生辉 2017年05月17日 17:29
 *
 * @version 1.0.0
 */
@Service("likesService")
public class LikesServiceImpl implements LikesService
{

    @Autowired
    private MomentCommentService momentCommentService;

    @Autowired
    private MomentService momentService;

    @Reference(version = "1.0.0",timeout = 3000)
    private LikesDubboService likesDubboService;

    @Reference(version = "1.0.0",timeout = 3000)
    private MomentsDubboService momentsDubboService;

    @Reference(version = "1.0.0",timeout = 3000)
    private MomentCommentDubboService momentCommentDubboService;

    private static final int VERSION = 1;

    @Override
    public int createLike(Likes likes)
    {
        if(checkLike(likes))
        {
            throw new BizException("你已经赞过该资源了");
        }
        Date date = new Date();
        likes.setCreateDate(date);
        likes.setModifyDate(date);
        likes.setCreator("Creator");
        likes.setModeifier("Modeifier");
        likes.setVersion(VERSION);
        likes.setDelFlag(false);
        return likesDubboService.createLike(likes);
    }

    @Override
    public boolean checkLike(Likes likes)
    {
        int result = likesDubboService.selectCountByExample(likes);
        if(result == 0)
        {
            return false;
        }
            return true;
    }

    @Override
    public int deleteLike(Likes likes)
    {
        return likesDubboService.deleteLike(likes);
    }
}
