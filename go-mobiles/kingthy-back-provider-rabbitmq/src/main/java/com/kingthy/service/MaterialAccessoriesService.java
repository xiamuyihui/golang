package com.kingthy.service;

import com.kingthy.dto.MaterialAccessoriesDTO;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 18:24 on 2017/9/20.
 * @Modified by:
 */
public interface MaterialAccessoriesService
{

    /**
     * 同步CIPP订单
     * @param message
     * @return
     * @throws Exception
     */
    int syncCIPPInfo(MaterialAccessoriesDTO message) throws Exception;
}
