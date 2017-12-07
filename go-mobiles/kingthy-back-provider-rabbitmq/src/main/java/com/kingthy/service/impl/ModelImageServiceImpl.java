package com.kingthy.service.impl;

import com.kingthy.dto.UploadModelImageDTO;
import com.kingthy.request.DownloadFileReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.DownloadFileService;
import com.kingthy.service.MessageService;
import com.kingthy.service.ModelImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ModelImageServiceImpl(描述其作用)
 * <p>
 * @author 赵生辉 2017年06月13日 16:54
 *
 * @version 1.0.0
 */
@Service
public class ModelImageServiceImpl implements ModelImageService
{

    @Autowired
    private DownloadFileService downloadFileService;

    @Override
    public void uploadModelImage(UploadModelImageDTO uploadModelImageDTO)
    {
        System.out.println("用户"+uploadModelImageDTO.getModelUuid()+"上传图片");
        DownloadFileReq downloadFileReq = new DownloadFileReq();
        WebApiResponse<String> response = downloadFileService.downloadFile(downloadFileReq);

        if (WebApiResponse.SUCCESS_CODE == response.getCode()){
            /*String url =  response.getData();
            WSMessageDTO wsMessageDTO = new WSMessageDTO();
            wsMessageDTO.setToken(uploadModelImageDTO.getToken());
            messageService.sendModelMessage(wsMessageDTO);*/
        }

    }
}
