package com.kingthy.service;

import com.kingthy.dto.IfoProcessInfoDTO;
import com.kingthy.response.WebApiResponse;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 14:49 on 2017/9/26.
 * @Modified by:
 */
public interface IfoProcessInfoService
{
    /**
     * 创建工艺工序
     * @param ifoProcessInfoDTO
     * @return
     * @throws Exception
     */
    WebApiResponse<?> createIfoProcess(IfoProcessInfoDTO ifoProcessInfoDTO) throws Exception;

}
