package com.kingthy.service.impl;

import com.kingthy.dto.GeometryFileAddressDTO;
import com.kingthy.dto.UploadFileRequest;
import com.kingthy.remote.FileUploadService;
import com.kingthy.request.PaperModelReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.GeometryFileService;
import com.kingthy.util.FileDealUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name PaperModelServiceImpl
 * @description 纸样实现类
 * @create 2017/10/16
 */
@Service(value = "paperModelService")
public class PaperModelServiceImpl implements GeometryFileService {
    private static final Logger logger= LoggerFactory.getLogger(PaperModelServiceImpl.class);
    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void filePath(Object object, String shell) {
        PaperModelReq paperModelReq = (PaperModelReq) object;
        String result;
        Scanner scanner = null;
        byte[] data = new byte[0];
        try {
            //执行脚本
            Process process = Runtime.getRuntime().exec(shell);
            InputStream inputStream = process.getInputStream();
            scanner = new Scanner(inputStream, "UTF-8");
            result = scanner.useDelimiter("\\A").next();
            //上传文件到文件服务器
            data = FileDealUtil.getFileByteData(result);
        } catch (IOException e) {
            logger.error(e.toString());
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        if(data.length>0) {
            UploadFileRequest uploadFileRequest = new UploadFileRequest();
            uploadFileRequest.setFileByte(data);
            WebApiResponse<String> response = fileUploadService.uploadFileByBinary(uploadFileRequest);
            if (response.getCode() == 0) {
                String fileUrl = response.getData();
                //将文件地址放入消息队列
                GeometryFileAddressDTO geometryFileAddressDTO = new GeometryFileAddressDTO();
                geometryFileAddressDTO.setUuid(paperModelReq.getUuid());
                geometryFileAddressDTO.setFileAddress(fileUrl);
                geometryFileAddressDTO.setType(2);
                rabbitTemplate.convertAndSend(paperModelReq.getExchange(), paperModelReq.getRoutingKey(), WebApiResponse.success(geometryFileAddressDTO));
            } else {
                rabbitTemplate.convertAndSend(paperModelReq.getExchange(), paperModelReq.getRoutingKey(), WebApiResponse.error("纸样文件出错,纸样uuid: " + paperModelReq.getUuid()));
            }
        }
    }
}
