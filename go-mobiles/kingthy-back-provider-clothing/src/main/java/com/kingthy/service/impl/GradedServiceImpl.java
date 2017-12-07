package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.command.CommandManager;
import com.kingthy.command.CommandResult;
import com.kingthy.command.CommandStatus;
import com.kingthy.conf.CommandSetting;
import com.kingthy.dubbo.user.service.FigureDubboService;
import com.kingthy.request.CalculatePriceReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.GradedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * 描述：GtadedServiceImpl
 * @author  likejie
 * @date 2017/10/10.
 */
@Service
public class GradedServiceImpl implements GradedService
{
    private static final Logger logger= LoggerFactory.getLogger(PriceServiceImpl.class);

    /**线程池对象*/
    private static final ExecutorService THREAD_POOL =  new ThreadPoolExecutor(
            8,
            200, 3,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(3),new ThreadPoolExecutor.DiscardPolicy());

    @Autowired
    private CommandManager commandManager;

    @Autowired
    private CommandSetting commandSetting;


    @Reference(version = "1.0.0",timeout = 3000)
    private FigureDubboService figureDubboService;

    @Override
    public WebApiResponse<?> gradedCommand(CalculatePriceReq req) {


        // 程序路径
        String command = commandSetting.getPriceCommandPath();



        //参数
        String[] parms={command,"1 2 3 4 5 6"};

        logger.info("开始执行命令");
        CommandResult result=commandManager.mtExecCommand(THREAD_POOL, parms);

        if(result.getStauts()== CommandStatus.SUCCESS.getValue()){

            return WebApiResponse.success(result.getOutput());
        }else{
            return WebApiResponse.error(result.getOutput());
        }

    }

}
