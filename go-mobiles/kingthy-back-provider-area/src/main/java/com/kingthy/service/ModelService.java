package com.kingthy.service;

import com.kingthy.entity.ModelImage;

/**
 * ModelService(描述其作用)
 * <p>
 * @author 赵生辉 2017年06月13日 14:03
 *
 * @version 1.0.0
 */
public interface ModelService
{
    /**
     * 发送图片
     * @param modelImage
     * @return
     */
    int sendImage(ModelImage modelImage);
}
