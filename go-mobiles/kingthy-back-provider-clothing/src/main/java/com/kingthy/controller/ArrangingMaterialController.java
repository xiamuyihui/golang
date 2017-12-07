package com.kingthy.controller;


import com.kingthy.request.ArrangingMaterialReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.GeometryFileService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name ArrangingMaterialController
 * @description 排料
 * @create 2017/10/13
 */
@RestController
@RequestMapping(value = "/arranging")
public class ArrangingMaterialController {

    @Resource(name = "arrangingMaterialService")
    private GeometryFileService arrangingMaterialService;

    @Value("${command.arranging.path}")
    private  String arrangingShell;

    @PostMapping(value = "/getFilePath")
    public WebApiResponse<?> getFilePath(@RequestBody ArrangingMaterialReq arrangingMaterialReq) {
        if (StringUtils.isEmpty(arrangingMaterialReq.getUuid())) {
            return WebApiResponse.error("uuid不能为空");
        }
        if (StringUtils.isEmpty(arrangingMaterialReq.getExchange())) {
            return WebApiResponse.error("exchange不能为空");
        }
        if (StringUtils.isEmpty(arrangingMaterialReq.getRoutingKey())) {
            return WebApiResponse.error("routingKey不能为空");
        }
        ExecutorService executorService = null;
        try {
            executorService = Executors.newFixedThreadPool(1);
            executorService.submit(() -> arrangingMaterialService.filePath(arrangingMaterialReq,arrangingShell));
            return WebApiResponse.success("正在生成排料文件");
        } catch (Exception e) {
            return WebApiResponse.error(e.getMessage());
        } finally {
            if (executorService != null){
                executorService.shutdown();
            }
        }
    }


}
