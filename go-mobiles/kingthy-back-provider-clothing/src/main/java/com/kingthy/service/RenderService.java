package com.kingthy.service;


import com.kingthy.request.ModelRenderReq;
import com.kingthy.request.ShowVideosReq;
import com.kingthy.response.WebApiResponse;

/**
 * 描述：渲染业务逻辑处理接口
 * @author  likejie
 * @date 2017/10/10.
 */
public interface RenderService {

    /**
     *  创建视频
     * @param  req
     * @return
     */
    WebApiResponse<?> createVideo(ShowVideosReq req);

    /**
     *  发送MQ消息
     */
    void sendMq();

    /**
     *  创建视频
     * @param  req
     * @return
     */
    WebApiResponse<?> modelRender(ModelRenderReq req);


}
