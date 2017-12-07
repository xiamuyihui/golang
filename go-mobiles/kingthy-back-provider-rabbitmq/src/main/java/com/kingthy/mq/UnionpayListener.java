package com.kingthy.mq;


import com.kingthy.response.WebApiResponse;
import com.kingthy.service.UnionPayementServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * 银联代付 消息消费
 * @author Created by likejie on 2017/6/20.
 */
@Component
public class UnionpayListener {

    private static final Logger logger = LoggerFactory.getLogger(UnionpayListener.class);
    /**定时任务****/
    private static final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    @Autowired
    UnionPayementServer unionPayementServer;

    public void receiveMessage(String orderId) {

        try {
            //查询订单状态
            boolean isSuccess= updateOrder(orderId);
            if(isSuccess){
                //订单交易成功
                logger.info("查询订单号成功，订单已支付");
            }else {

            }

        } catch (Exception ex) {

            logger.error("CacheUpdateListener.receiveMessage error",ex.toString());
        }
    }
    private  void scheduledUpdateOrder(String orderId) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {


            }
        };
        scheduledExecutorService.schedule(runnable,  1, TimeUnit.MINUTES);
    }
    private boolean updateOrder(String orderId){

        WebApiResponse<?> response= unionPayementServer.queryTranStatus(orderId);
        return response.getCode()==0;
    }
}
