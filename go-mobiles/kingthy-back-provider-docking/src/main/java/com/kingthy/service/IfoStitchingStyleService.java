package com.kingthy.service;

import com.kingthy.dto.IfoStitchingStyleDTO;
import com.kingthy.response.WebApiResponse;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 14:27 on 2017/9/26.
 * @Modified by:
 */
public interface IfoStitchingStyleService
{
    /**
     * 创建缝合关系
     * @param ifoStitchingStyleDTO
     * @return
     * @throws Exception
     */
    WebApiResponse<?> createStitchingStyle(IfoStitchingStyleDTO ifoStitchingStyleDTO) throws Exception;
}
