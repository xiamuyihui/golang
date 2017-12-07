package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.kingthy.cache.RedisManager;
import com.kingthy.dubbo.user.service.ShowVideosDubboService;
import com.kingthy.entity.ShowVideos;
import com.kingthy.request.ShowVideosReq;
import com.kingthy.service.ShowVideosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * 走秀视频
 * @author likejie
 * @date 2017/9/11.
 */
@Service
public class ShowVideosServiceImpl implements ShowVideosService {


    @Reference(version = "1.0.0",timeout = 3000)
    private ShowVideosDubboService showVideosDubboService;

    @Autowired
    private RedisManager redisManager;

    @Override
    public int addShowVideos(ShowVideosReq req) {
        ShowVideos showVideos=new ShowVideos();
        showVideos.setMemberUuid(req.getMemberUuid());
        showVideos.setName(req.getName());
        showVideos.setVideoUrl(req.getVideoUrl());
        return showVideosDubboService.insert(showVideos);
    }

    @Override
    public PageInfo<ShowVideos> queryPage(ShowVideosReq req) {




        ShowVideos cond=new ShowVideos();
        cond.setMemberUuid(req.getMemberUuid());
        return showVideosDubboService.queryPage(req.getPageNum(),req.getPageSize(),cond);
    }

    @Override
    public int deleteShowVideos(String uuid) {
        ShowVideos showVideos=new ShowVideos();
        showVideos.setDelFlag(false);
        showVideos.setUuid(uuid);
        return showVideosDubboService.updateByUuid(showVideos);
    }
}
