package com.kingthy.job;

import com.kingthy.service.IncomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定义查询银联提现结果接口
 * @author xumin
 * @Description:
 * @DATE Created by 15:05 on 2017/6/23.
 * @Modified by:
 */
@Component
@Configurable
@EnableScheduling
public class WithdrawsCashTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(WithdrawsCashTask.class);

    @Autowired
    private IncomeService incomeService;

    @Scheduled(cron = "0 */1 * * * *")
    public void taskMemberIncome(){

        try{
            incomeService.updateStatusTask();
        }catch (Exception e){
            LOGGER.error("定义查询银联提现结果接口 失败" + e);
        }

    }
}
