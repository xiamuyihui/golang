package com.kingthy.controller;

import com.kingthy.request.PaperModelReq;
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
 * @class_name PaperModelController
 * @description 纸样控制层
 * @create 2017/10/13
 */
@RestController
@RequestMapping("/paperModel")
public class PaperModelController {
    @Resource(name = "paperModelService")
    private GeometryFileService paperModelService;

    @Value("${command.paperModel.path}")
    private  String paperModelShell;
    /**线程池对象*/
    private static final ExecutorService THREAD_POOL =  new ThreadPoolExecutor(
            8,
            200, 3,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(3),new ThreadPoolExecutor.DiscardPolicy());
    @PostMapping(value = "/getFilePath")
    public WebApiResponse<?> getFilePath(@RequestBody PaperModelReq paperModelReq){
        if (StringUtils.isEmpty(paperModelReq.getUuid())){
            return WebApiResponse.error("uuid不能为空");
        }
        if (StringUtils.isEmpty(paperModelReq.getExchange())){
            return WebApiResponse.error("exchange不能为空");
        }
        if (StringUtils.isEmpty(paperModelReq.getRoutingKey())){
            return WebApiResponse.error("routingKey不能为空");
        }
        ExecutorService executorService =null;
        try {
            THREAD_POOL.submit(() -> paperModelService.filePath(paperModelReq,paperModelShell));
            return WebApiResponse.success("正在生成纸样文件");
        } catch (Exception e) {
            return WebApiResponse.error(e.getMessage());
        }finally {
            if (executorService != null){
                executorService.shutdown();
            }
        }
    }
}
