package com.kingthy.service;

import com.kingthy.dto.NcpltInfoDTO;
import com.kingthy.response.WebApiResponse;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 13:48 on 2017/10/11.
 * @Modified by:
 */
public interface NcPltInfoService
{
    /**
     * 生成排料后的纸样文件和裁床文件
     * @param dto
     * @return
     * @throws Exception
     */
    WebApiResponse<?> createNcpltInfo(NcpltInfoDTO dto) throws Exception;

    /**
     * 查询 生成排料后的纸样文件和裁床文件 结果
     * @param dto
     * @return
     * @throws Exception
     */
    WebApiResponse<?> ncpltInfo(NcpltInfoDTO dto) throws Exception;
}
