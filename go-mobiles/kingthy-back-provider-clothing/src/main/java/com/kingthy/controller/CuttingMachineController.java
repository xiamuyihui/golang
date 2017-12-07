package com.kingthy.controller;

import com.kingthy.request.CuttingMachineReq;
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
 * @class_name CuttingMachineController
 * @description 裁床
 * @create 2017/10/13
 */
@RestController
@RequestMapping("/cuttingMachine")
public class CuttingMachineController {
    @Resource(name = "cuttingMachineService")
    private GeometryFileService cuttingMachineService;

    @Value("${command.cuttingMachine.path}")
    private  String cuttingMachineShell;

    @PostMapping(value = "/getFilePath")
    public WebApiResponse<?> getFilePath(@RequestBody CuttingMachineReq cuttingMachineReq) {
        if (StringUtils.isEmpty(cuttingMachineReq.getUuid())) {
            return WebApiResponse.error("uuid不能为空");
        }
        if (StringUtils.isEmpty(cuttingMachineReq.getExchange())) {
            return WebApiResponse.error("exchange不能为空");
        }
        if (StringUtils.isEmpty(cuttingMachineReq.getRoutingKey())) {
            return WebApiResponse.error("routingKey不能为空");
        }
        ExecutorService executorService = null;
        try {
            executorService = Executors.newFixedThreadPool(1);
            executorService.submit(() -> cuttingMachineService.filePath(cuttingMachineReq,cuttingMachineShell));
            return WebApiResponse.success("正在生成裁床文件");
        } catch (Exception e) {
            return WebApiResponse.error(e.getMessage());
        } finally {
            if (executorService != null){
                executorService.shutdown();
            }
        }
    }
}
