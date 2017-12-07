package com.kingthy.service;

import com.kingthy.request.BindingBankReq;
import com.kingthy.request.IncomeAddReq;
import com.kingthy.request.UpdateWithDrawStatusReq;
import com.kingthy.request.WithdrawReq;
import com.kingthy.response.WebApiResponse;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 10:24 on 2017/6/12.
 * @Modified by:
 */
public interface IncomeService {

    /**
     * 查询收益记录列表
     * @param pageNo
     * @param pageSize
     * @param token
     * @return
     * @throws Exception
     */
    WebApiResponse<?> incomeList(Integer pageNo, Integer pageSize, String token) throws Exception;

    /**
     * 查询提现记录列表
     * @param pageNo
     * @param pageSize
     * @param token
     * @return
     * @throws Exception
     */
    WebApiResponse<?> withdrawsCashList(Integer pageNo, Integer pageSize, String token) throws Exception;

    /**
     * 查询当前余额
     * @param token
     * @return
     * @throws Exception
     */
    WebApiResponse<?> queryBalance(String token) throws Exception;

    /**
     * 查询绑定的银行卡
     * @param token
     * @return
     * @throws Exception
     */
    WebApiResponse<?> queryBankInfo(String token) throws Exception;

    /**
     * 增加收益
     * @param req
     * @return
     * @throws Exception
     */
    WebApiResponse<?> addIncome(IncomeAddReq req) throws Exception;

    /**
     * 绑定银行卡
     * @param req
     * @return
     * @throws Exception
     */
    WebApiResponse<?> bindingBankCard(BindingBankReq req) throws Exception;

    /**
     * 提现到银行卡
     * @param req
     * @return
     * @throws Exception
     */
    WebApiResponse<?> withdraw(WithdrawReq req) throws Exception;

    /**
     * 更新提现状态及流水号
     * @param req
     * @return
     * @throws Exception
     */
    WebApiResponse<?> updateStatusAndOrderId(UpdateWithDrawStatusReq req) throws Exception;

    /**
     * 定时任务 查询金额是否到账 并 更新数据库
     * @return
     * @throws Exception
     */
    void updateStatusTask() throws Exception;
}
