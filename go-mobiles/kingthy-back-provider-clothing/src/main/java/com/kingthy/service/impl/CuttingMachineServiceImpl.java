package com.kingthy.service.impl;

import com.kingthy.dto.GeometryFileAddressDTO;
import com.kingthy.dto.UploadFileRequest;
import com.kingthy.remote.FileUploadService;
import com.kingthy.request.CuttingMachineReq;
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
 * @class_name CuttingMachineServiceImpl
 * @description 裁床实现类
 * @create 2017/10/16
 */
@Service("cuttingMachineService")
public class CuttingMachineServiceImpl implements GeometryFileService {
    private static final Logger logger= LoggerFactory.getLogger(CuttingMachineServiceImpl.class);
    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void filePath(Object object, String shell) {
        CuttingMachineReq cuttingMachineReq = (CuttingMachineReq) object;
        String result;
        Scanner scanner = null;
        byte[] data = new byte[0];
        try {
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
                geometryFileAddressDTO.setUuid(cuttingMachineReq.getUuid());
                geometryFileAddressDTO.setFileAddress(fileUrl);
                geometryFileAddressDTO.setType(1);
                rabbitTemplate.convertAndSend(cuttingMachineReq.getExchange(), cuttingMachineReq.getRoutingKey(), WebApiResponse.success(geometryFileAddressDTO));
            } else {
                rabbitTemplate.convertAndSend(cuttingMachineReq.getExchange(), cuttingMachineReq.getRoutingKey(), WebApiResponse.error("裁床文件出错,排料uuid: " + cuttingMachineReq.getUuid()));
            }
        }
    }
}
