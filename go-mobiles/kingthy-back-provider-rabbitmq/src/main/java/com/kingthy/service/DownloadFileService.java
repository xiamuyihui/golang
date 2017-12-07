package com.kingthy.service;

import com.kingthy.request.DownloadFileReq;
import com.kingthy.response.WebApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * UploadFileService(描述其作用)
 * <p>
 * @author 赵生辉 2017年06月16日 18:05
 *
 * @version 1.0.0
 */
@FeignClient(name="upload-file-dubbo", fallback = DownloadFileService.HystrixClientFallbackImpl.class)
public interface DownloadFileService
{

        /**
         * 下载文件
         * @param downloadFileReq
         * @return
         */
        @RequestMapping(value = "/fileDownload/download",method = RequestMethod.POST)
        WebApiResponse<String> downloadFile(@RequestBody DownloadFileReq downloadFileReq);

        @Component
        class HystrixClientFallbackImpl implements DownloadFileService{

                private static final Logger LOGGER = LoggerFactory.getLogger(DownloadFileService.HystrixClientFallbackImpl.class);

                @Override
                public WebApiResponse<String> downloadFile(DownloadFileReq downloadFileReq)
                {
                        HystrixClientFallbackImpl.LOGGER.info("异常发生，进入fallback方法，接收的参数：", downloadFileReq.toString());
                        WebApiResponse<String> webApiResponse = new WebApiResponse<>();
                        webApiResponse.setCode(-99);
                        webApiResponse.setMessage("无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
                        return webApiResponse;
                }
        }
}
