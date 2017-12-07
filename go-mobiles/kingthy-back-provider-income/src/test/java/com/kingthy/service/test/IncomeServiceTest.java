package com.kingthy.service.test;

import com.kingthy.KingthyProviderIncomeApplication;
import com.kingthy.request.BindingBankReq;
import com.kingthy.request.IncomeAddReq;
import com.kingthy.request.WithdrawReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.IncomeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 17:40 on 2017/6/16.
 * @Modified by:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = KingthyProviderIncomeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"eureka.client.enabled=false"})
@Rollback
@Transactional
public class IncomeServiceTest {

    private static String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1bmlxdWVfbmFtZSI6IjE1MjAwMDAwMzc3IiwiaXNzIjoicmVzdGFwaXVzZXIiLCJhdWQiOiIwOThmNmJjZDQ2MjFkMzczY2FkZTRlODMyNjI3YjRmNiIsImV4cCI6MTQ5Nzk0ODU3MiwibmJmIjoxNDk3OTQxMzcyfQ.L5OKeKQ9DvWJnBUlOfju_OSv9xIye5S0bj5_Mnj4VGc";

    @Autowired
    private IncomeService incomeService;


    /**
     * 查询余额测试用例
     */
    @Test
    public void queryBalance(){

        try{

            WebApiResponse<?> response = incomeService.queryBalance(token);

            Assert.assertTrue("success", response.getCode() == 0 );

        }catch (Exception e){
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

    }

    /**
     * 收益记录测试用例
     */
    @Test
    public void incomeList(){
        try{
            WebApiResponse<?> response = incomeService.incomeList(0, 10, token);

            Assert.assertTrue("success", response.getCode() == 0 );
        }catch (Exception e){
            e.printStackTrace();

            Assert.fail(e.getMessage());
        }

    }

    /**
     * 查询提现记录列表测试用例
     */
    @Test
    public void withdrawsCashList(){
        try{

            WebApiResponse<?> response = incomeService.withdrawsCashList(0, 10, token);

            Assert.assertTrue("success", response.getCode() == 0 );
        }catch (Exception e){
            e.printStackTrace();

            Assert.fail(e.getMessage());
        }

    }

    /**
     * 增加收益
     */
    @Test
    public void addIncome(){

        try {

            IncomeAddReq req = new IncomeAddReq();

            req.setOrderSn("2017061521156731");
            req.setMemberUuid("97137901347464904");

            WebApiResponse<?> response = incomeService.addIncome(req);

            Assert.assertTrue("success", response.getCode() == 0);

        }catch (Exception e){
            e.printStackTrace();

            Assert.fail(e.getMessage());
        }
    }

    /**
     * 绑定银行卡
     */
    @Test
    public void bindingBankCard(){

        try{

            BindingBankReq req = new BindingBankReq();
            req.setToken(token);

            req.setBankCode("test001");
            req.setBankName("中国银行");
            req.setBankCode("622575558777213");
            req.setCardholder("徐敏");
            req.setIdentityCard("4211561990132354X");
            req.setPhone("13916445873");

            WebApiResponse<?> response = incomeService.bindingBankCard(req);

            Assert.assertTrue("success", response.getCode() == 0);

        }catch (Exception e){
            e.printStackTrace();

            Assert.fail(e.getMessage());
        }
    }

    /**
     * 提现到银行卡
     */
    @Test
    public void withdraw(){

        try{
            WithdrawReq req = new WithdrawReq();

            req.setBankCardUuid("");
            req.setMoney(10d);
            req.setToken(token);
            WebApiResponse<?> response = incomeService.withdraw(req);

            Assert.assertTrue("success", response.getCode() == 0);

        }catch (Exception e){
            e.printStackTrace();

            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void updateStatusAndOrderId(){
        try {

        }catch (Exception e){
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
}
