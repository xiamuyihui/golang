package com.kingthy.service;

import com.kingthy.dto.UploadModelImageDTO;

/**
 * ModelImageService(描述其作用)
 * <p>
 * @author 赵生辉 2017年06月13日 16:53
 *
 * @version 1.0.0
 */
public interface ModelImageService
{
    /**
     * 上传模型图片
     * @param uploadModelImageDTO
     */
    void uploadModelImage(UploadModelImageDTO uploadModelImageDTO);
}
