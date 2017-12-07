package com.kingthy.service;

import com.kingthy.response.EncryptUploadDTO;
import com.kingthy.entity.ModelImage;
import com.kingthy.response.CreaterModelRes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 上传图片生成模型
 * <p>
 * @author 赵生辉 2017年06月13日 14:03
 *
 * @version 1.0.0
 */
public interface ModelService
{
    /**
     * 上传图片生成模型
     * @param modelImage
     * @return
     */
    int sendImage(ModelImage modelImage);

    CreaterModelRes createrModel(MultipartFile multipartFile,String memberUuid)
        throws IOException;
}
