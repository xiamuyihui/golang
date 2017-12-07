package com.kingthy.controller;

import com.kingthy.cache.RedisManager;
import com.kingthy.common.CommonUtils;
import com.kingthy.dto.RealNameVerifiedDTO;
import com.kingthy.dto.TransferDTO;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.UnionPayementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;


/**
 * @author  by likejie on 2017/6/16.
 */
@Api
@RestController
@RequestMapping("/unionpay")
public class UnionPayController {

    private static final Logger logger = LoggerFactory.getLogger(UnionPayController.class);
    private static final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    @Autowired
    private UnionPayementService unionPayementService;
    @Autowired
    private RedisManager redisManager;

    @ApiOperation(value = "银联代付请求", notes = "银联代付请求接口")
    @PostMapping("/sendTransferRequest")
    public WebApiResponse<?> sendTransferRequest(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
            @RequestBody TransferDTO dto) {

        try {
            String memberUuid = redisManager.get(token);
            if (StringUtils.isBlank(memberUuid)) {
                return WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
            }
            if (StringUtils.isBlank(dto.getAccNo())) {
                return WebApiResponse.error("银行卡号不能为空");
            }
            if (StringUtils.isBlank(dto.getCertifId())) {
                return WebApiResponse.error("身份证号不能为空");
            }
            if (StringUtils.isBlank(dto.getOrderId())) {
                return WebApiResponse.error("订单号不能为空");
            }
            if (dto.getAmount() <= 0) {

                return WebApiResponse.error("金额应大于0");
            }
            dto.setMemberUuid(memberUuid);
            return unionPayementService.sendTransferRequest(dto);

        } catch (Exception ex) {
            logger.error("UnionPayController.sendTransferRequest error:", ex);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }

    }
    @ApiOperation(value = "查询银联订单状态", notes = "查询银联订单状态")
    @GetMapping("/queryTranStatus/{orderId}")
    public WebApiResponse<?> queryTranStatus(@PathVariable String orderId) {

        try{
            if(StringUtils.isBlank(orderId)){
                return WebApiResponse.error("订单号不能为空");
            }
            return unionPayementService.queryTranStatus(orderId);
        }catch (Exception ex){
            logger.error("UnionPayController.queryTranStatus error:",ex);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }


    }
    @ApiOperation(value = "实名认证", notes = "实名认证")
    @PostMapping("/realNameVerified")
    public WebApiResponse<?> realNameVerified(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
            @RequestBody RealNameVerifiedDTO dto) {

        try {
            String memberUuid = redisManager.get(token);
            if (StringUtils.isBlank(memberUuid)) {
                return WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
            }
            if(StringUtils.isBlank(dto.getAccNo())){
                return WebApiResponse.error("银行卡号不能为空");
            }
            if(StringUtils.isBlank(dto.getCertifId())){
                return WebApiResponse.error("身份证号不能为空");
            }
            if(StringUtils.isBlank(dto.getCustomerNm())){
                return WebApiResponse.error("客户名称不能为空");
            }
            if(StringUtils.isBlank(dto.getPhone())){
                return WebApiResponse.error("手机号不能为空");
            }
            dto.setMemberUuid(memberUuid);
            return unionPayementService.realNameVerified(dto);
        } catch (Exception ex) {
            logger.error("UnionPayController.realNameVerified error:", ex);
            return WebApiResponse.error(WebApiResponse.ResponseMsg.EXCEPTION.getValue());
        }

    }


    @ApiOperation(value = "银联代付回调", notes = "银联代付回调")
    @PostMapping("/unionTransferCallBack")
    public void unionTransferCallBack(HttpServletRequest request, HttpServletResponse response) {


        /**
         * 1. 商户返回码200表示通知成功
         * 2. 10秒内银联没收到应答，判定通知失败
         * 3. 第一次通知失败后，银联会重发，最多发送五次。
         **/
        //返回参数
        Map<String, String> res = new HashMap<>(1);
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                System.out.println("回调接收参数"+en+"值："+value);
                res.put(en, value);
                // 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                if (res.get(en) == null || "".equals(res.get(en))) {
                    res.remove(en);
                }


            }
        }
        /*******/

    }
}
