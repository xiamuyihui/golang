package com.kingthy.service;

import com.kingthy.dto.IfoOrderDetailBomDTO;
import com.kingthy.response.WebApiResponse;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 20:27 on 2017/9/25.
 * @Modified by:
 */
public interface IfoOrderDetailBomService
{

    /**
     * 创建BOM信息
     * @param detailBomDTO
     * @return
     * @throws Exception
     */
    WebApiResponse<?> createOrderItemBom(IfoOrderDetailBomDTO detailBomDTO) throws Exception;
}
