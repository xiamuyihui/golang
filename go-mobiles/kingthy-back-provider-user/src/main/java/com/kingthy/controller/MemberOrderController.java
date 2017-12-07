/**
 * 系统项目名称
 * com.kingthy.controller
 * MemberOrderController.java
 * 
 * 2017年4月24日-上午9:46:54
 *  2017金昔科技有限公司-版权所有
 *
 */
package com.kingthy.controller;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.kingthy.cache.RedisManager;
import com.kingthy.common.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.kingthy.dto.MemberOrderDTO;
import com.kingthy.page.PageT;
import com.kingthy.request.MemberOrderReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.response.WebApiResponse.ResponseMsg;
import com.kingthy.service.MemberOrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 *
 * @author 李克杰 2017年4月24日 上午9:46:54
 * 
 * @version 1.0.0
 *
 */
@Api
@RestController
@RequestMapping("/memberOrder")
public class MemberOrderController 
{
    private static final Logger LOG = LoggerFactory.getLogger(MemberOrderController.class);
    
    @Autowired
    private MemberOrderService morderService;
    
    @Autowired
    private RedisManager redisManager;
    
    @ApiOperation(value = "根据订单状态，获取会员订单", notes = "")
    @GetMapping("/getMemberOrderList/{orderStatus}")
    public WebApiResponse<?> getMemberOrderList(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
        @PathVariable @ApiParam(name = "orderStatus", value = "订单状态   -1：所有, 0:代付款 1:生产中 2:待发货 3:待签收 4:待评价 5:已取消 6:待退单 7:已完成", required = true) Integer orderStatus) {
        try {
            // 从缓存获取token
            String memberUuid = redisManager.get(token);
            if (orderStatus == -1) {
                orderStatus = null;
            }
            if (StringUtils.isNotBlank(memberUuid)) {
                List<MemberOrderDTO> list = morderService.getMemberOrderList(memberUuid, orderStatus);
                return WebApiResponse.success(list);
            } else {
                return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
            }
        } catch (Exception e) {
            LOG.error("MemberOrderController.getMemberOrderList error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }

    }
    
    @ApiOperation(value = "分页查询会员订单", notes = "以时间节点，获取分页数据")
    @PostMapping("/pageGetOrderList")
    public WebApiResponse<?> pageGetOrderList(
            @RequestHeader(CommonUtils.REQUEST_HEADER_PARAMETER) String token,
            @RequestBody MemberOrderReq req)
    {
        try
        {
            // 从缓存获取token
            String memberUuid = redisManager.get(token);
            if(StringUtils.isBlank(memberUuid)){
                return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
            }
            if (StringUtils.isNotBlank(memberUuid))
            {
                req.setMemberUuid(memberUuid);
                PageInfo<MemberOrderDTO> data = morderService.pageGetOrderList(req);
                return WebApiResponse.success(data);
            }
            else
            {
                return WebApiResponse.error(ResponseMsg.TOKEN_ERROR.getValue());
            }
        }
        catch (Exception e)
        {
            LOG.error("MemberOrderController.pageQueryMemberOrders error:" + e.toString());
            return WebApiResponse.error(ResponseMsg.EXCEPTION.getValue());
        }
    }
}
