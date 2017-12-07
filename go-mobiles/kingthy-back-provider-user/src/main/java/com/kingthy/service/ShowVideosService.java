package com.kingthy.service;

import com.github.pagehelper.PageInfo;
import com.kingthy.entity.ShowVideos;
import com.kingthy.request.ShowVideosReq;

/**
 * @author  likejie
 * @date 2017/9/11.
 */
public interface ShowVideosService {


    /**
     * 增加视频
     * @param req
     * @return
     */
    int addShowVideos(ShowVideosReq req);
    /**
     * 分页查询
     * @param req
     * @return
     */
    PageInfo<ShowVideos> queryPage(ShowVideosReq req);
    /**
     * 删除
     * @param uuid
     * @return
     */
    int deleteShowVideos(String uuid);
}
