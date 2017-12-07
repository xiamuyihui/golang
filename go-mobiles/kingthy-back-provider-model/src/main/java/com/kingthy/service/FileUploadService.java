package com.kingthy.service;


import com.kingthy.dto.UploadFileRequest;
import com.kingthy.response.WebApiResponse;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 描述：文件上传服务
 * @author  likejie
 * @date 2017/10/10.
 */
@FeignClient(name = "upload-file-dubbo", fallback = FileUploadService.HystrixClientFallbackImpl.class)
public interface FileUploadService
{

    Logger LOG = LoggerFactory.getLogger(FileUploadService.class);

    /**
     * 上传文件（二进制流）
     * @param  req
     * @return  WebApiResponse
     */
    @RequestMapping(value = "/fileUpload/uploadFileByBinary", method = RequestMethod.POST)
    WebApiResponse<String> uploadFileByBinary(@RequestBody UploadFileRequest req);

    /**
     * 获取文件大小
     * @param  file
     * @return  WebApiResponse
     */
    @RequestMapping(value = "/fileUpload/getFileSize", method = RequestMethod.POST)
    WebApiResponse<?> localHostUploadFile(@RequestParam("file") @ApiParam("二进制流格式") MultipartFile file);

    /**
     * 获取文件大小
     * @param  fileRequest
     * @return  WebApiResponse
     */
    @RequestMapping(value = "/fileUpload/encryptUploadFile", method = RequestMethod.POST)
    WebApiResponse<?> getFileSize(@RequestBody UploadFileRequest fileRequest);

    @Component
    class HystrixClientFallbackImpl implements FileUploadService
    {
        @Override
        public WebApiResponse<String> uploadFileByBinary( UploadFileRequest req){
            LOG.error("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return WebApiResponse.error("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
        }

        @Override
        public WebApiResponse<?> getFileSize(UploadFileRequest fileRequest) {
            LOG.error("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return WebApiResponse.error("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
        }

        @Override
        public WebApiResponse<?> localHostUploadFile(MultipartFile file) {
            LOG.error("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
            return WebApiResponse.error("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
        }
    }
}
