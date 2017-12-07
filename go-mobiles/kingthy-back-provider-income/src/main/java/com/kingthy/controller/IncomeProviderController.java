package com.kingthy.controller;

import com.kingthy.common.CommonUtils;
import com.kingthy.request.BindingBankReq;
import com.kingthy.request.IncomeAddReq;
import com.kingthy.request.UpdateWithDrawStatusReq;
import com.kingthy.request.WithdrawReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.IncomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 9:53 on 2017/6/12.
 * @Modified by:
 */
@Api
@RestController
@RequestMapping("income")
public class IncomeProviderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IncomeProviderController.class);

    @Autowired
    private IncomeService incomeService;

    @ApiOperation(value = "查询收益记录列表", notes = "")
    @RequestMapping(value = "/list/{pageNo}/{pageSize}", method = RequestMethod.GET)
    public WebApiResponse<?> incomeList(@PathVariable @ApiParam(name = "pageNo", value = "pageNo", required = true) Integer pageNo,
                                        @PathVariable @ApiParam(name = "pageSize", value = "pageSize", required = true) Integer pageSize,
                                        @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) @ApiParam(name = "token", value = "token", required = true)String token){

        WebApiResponse<?> result = null;

        try {

            result = incomeService.incomeList(pageNo, pageSize, token);

        }catch (Exception e){
            LOGGER.error("-------------查询收益记录列表 失败: "+e+ "-------------");
            result = WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }

        return result;
    }

    @ApiOperation(value = "查询提现记录列表", notes = "")
    @RequestMapping(value = "/cash/{pageNo}/{pageSize}", method = RequestMethod.GET)
    public WebApiResponse<?> withdrawsCashList(@PathVariable @ApiParam(name = "pageNo", value = "pageNo", required = true) Integer pageNo,
                                               @PathVariable @ApiParam(name = "pageSize", value = "pageSize", required = true) Integer pageSize,
                                               @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER)  @ApiParam(name = "token", value = "token", required = true)String token){
        WebApiResponse<?> result = null;

        try {

            result = incomeService.withdrawsCashList(pageNo, pageSize, token);

        }catch (Exception e){
            LOGGER.error("-------------查询提现记录列表 失败: "+e+ "-------------");
            result = WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }

        return result;
    }

    @ApiOperation(value = "查询当前余额", notes = "")
    @RequestMapping(value = "/balance", method = RequestMethod.GET)
    public WebApiResponse<?> queryBalance(@RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) @ApiParam(name = "token", value = "token", required = true)String token){

        WebApiResponse<?> result = null;

        try {

            result = incomeService.queryBalance(token);

        }catch (Exception e){
            LOGGER.error("-------------查询当前余额 失败: "+e+ "-------------");
            result = WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }

        return result;
    }

    /**
     * 增加收益
     * @param req
     * @return
     */
    @ApiOperation(value = "订单完成时增加收益", notes = "")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public WebApiResponse<?> addIncome(@RequestBody @ApiParam(value = "增加收益", required = true)IncomeAddReq req,
                                       @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER)  @ApiParam(name = "token", value = "token", required = true)String token){

        WebApiResponse<?> result = null;

        if (StringUtils.isEmpty(req.getMemberUuid()) || StringUtils.isEmpty(req.getOrderSn())){
            return WebApiResponse.error(WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
        }

        try {

            req.setToken(token);
            result = incomeService.addIncome(req);

        }catch (Exception e){
            LOGGER.error("-------------增加收益 失败: "+e+ "-------------");
            result = WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }

        return result;
    }

    /**
     * 绑定银行卡
     * @param req
     * @return
     */
    @ApiOperation(value = "绑定银行卡", notes = "")
    @RequestMapping(value = "/binding/bank", method = RequestMethod.POST)
    public WebApiResponse<?> bindingBankCard(@RequestBody @ApiParam(value = "绑定银行卡", required = true)BindingBankReq req,
                                             @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER)  @ApiParam(name = "token", value = "token", required = true)String token){

        WebApiResponse<?> result = null;

        if (StringUtils.isEmpty(token)){
            return WebApiResponse.error(WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
        }

        try{

            req.setToken(token);
            result = incomeService.bindingBankCard(req);

        }catch (Exception e){
            LOGGER.error("-------------绑定银行卡 失败: "+e+ "-------------");
            result = WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }

        return result;

    }

    /**
     * 余额提现
     * @param req
     */
    @ApiOperation(value = "余额提现")
    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public WebApiResponse<?> withdraw(@RequestBody @ApiParam(value = "余额提现", required = true)WithdrawReq req,
                                      @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER)  @ApiParam(name = "token", value = "token", required = true)String token){

        WebApiResponse<?> result = null;

        try {

            req.setToken(token);
            result = incomeService.withdraw(req);

        }catch (Exception e){
            LOGGER.error("-------------提现到银行卡 失败: "+e+ "-------------");
            result = WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }
        return result;
    }


    /**
     * 更新提现状态及流水号
     * @param req
     * @return
     */
    @ApiOperation(value = "更新提现状态及流水号")
    @RequestMapping(value = "/update/withdraw", method = RequestMethod.POST)
    public WebApiResponse<?> updateStatusAndOrderId(@RequestBody @ApiParam(value = "更新提现状态及流水号", required = true)UpdateWithDrawStatusReq req,
                                                    @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER)  @ApiParam(name = "token", value = "token", required = true)String token){

        WebApiResponse<?> result = null;

        try {

            req.setToken(token);
            result = incomeService.updateStatusAndOrderId(req);

        }catch (Exception e){
            LOGGER.error("-------------更新提现状态及流水号 失败: "+e+ "-------------");
            result = WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }

        return result;
    }

    @ApiOperation(value = "查询绑定的银行卡")
    @RequestMapping(value = "/bank", method = RequestMethod.POST)
    public WebApiResponse<?> queryBankInfo(@RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER)  @ApiParam(name = "token", value = "token", required = true)String token){

        WebApiResponse<?> result = null;

        try {

            result = incomeService.queryBankInfo(token);

        }catch (Exception e){
            LOGGER.error("-------------查询绑定的银行卡 失败: "+e+ "-------------");
            result = WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }

        return result;

    }

}