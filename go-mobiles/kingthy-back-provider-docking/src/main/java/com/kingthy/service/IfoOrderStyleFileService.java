package com.kingthy.service;

import com.kingthy.dto.IfoOrderStyleFileDTO;
import com.kingthy.response.WebApiResponse;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 11:52 on 2017/9/26.
 * @Modified by:
 */
public interface IfoOrderStyleFileService
{
    /**
     * 款式文件接口表
     * @param ifoOrderStyleFileDTO
     * @return
     * @throws Exception
     */
    WebApiResponse<?> createOrderStyleFile(IfoOrderStyleFileDTO ifoOrderStyleFileDTO) throws Exception;
}
