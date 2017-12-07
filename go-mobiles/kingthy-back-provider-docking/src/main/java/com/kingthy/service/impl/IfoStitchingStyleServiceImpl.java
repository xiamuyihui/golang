package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.common.CommonUtils;
import com.kingthy.dto.IfoStitchingStyleDTO;
import com.kingthy.dubbo.docking.service.IfoStitchingStyleDubboService;
import com.kingthy.dubbo.order.service.OrderDubboService;
import com.kingthy.entity.IfoOrderStitchingInfo;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.IfoStitchingStyleService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xumin
 * @Description:缝制工艺工序接口表
 * @DATE Created by 14:28 on 2017/9/26.
 * @Modified by:
 */

@Service
public class IfoStitchingStyleServiceImpl implements IfoStitchingStyleService {

    @Reference(version = "1.0.0", timeout = 3000)
    private IfoStitchingStyleDubboService ifoStitchingStyleDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private OrderDubboService orderDubboService;

    @Override
    public WebApiResponse<?> createStitchingStyle(IfoStitchingStyleDTO ifoStitchingStyleDTO) throws Exception {

        if (ifoStitchingStyleDTO == null || ifoStitchingStyleDTO.getStitchingInfoList() == null
                || ifoStitchingStyleDTO.getStitchingInfoList().isEmpty() || StringUtils.isEmpty(ifoStitchingStyleDTO.getOrderItemSn()))
        {
            return WebApiResponse.error(WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
        }

        //查询子订单是否 是支付成功的状态
        Long count = orderDubboService.selectOrdersCountByOrderItemSn(ifoStitchingStyleDTO.getOrderItemSn());

        if (count == null || count <= 0){
//            return WebApiResponse.error("订单状态不正确");
        }
        List<IfoOrderStitchingInfo> stitchingStyles = new ArrayList<>();

        for (IfoStitchingStyleDTO.StitchingInfo stitchingInfo : ifoStitchingStyleDTO.getStitchingInfoList()){

            IfoOrderStitchingInfo t = new IfoOrderStitchingInfo();
            t.setStitchingCode(stitchingInfo.getStitchingCode());
            t.setStitchingDesc(stitchingInfo.getStitchingDesc());
            t.setPiecesCodes(stitchingInfo.getPiecesCodes());
            t.setPartsCodes(stitchingInfo.getPartsCodes());
            t.setStandardCodes(stitchingInfo.getStandardCodes());
            t.setOrderItemSn(ifoStitchingStyleDTO.getOrderItemSn());
            t.setCreateTime(new Date());
            t.setUpdateId(CommonUtils.updaterId);
            t.setOperSt(IfoOrderStitchingInfo.OperStType.add.getValue());
            t.setIfStatus(IfoOrderStitchingInfo.StatusType.init.getValue());

            stitchingStyles.add(t);
        }

        int result = ifoStitchingStyleDubboService.insertList(stitchingStyles);

        return result > 0 ? WebApiResponse.success(WebApiResponse.ResponseMsg.SUCCESS.getValue())
                : WebApiResponse.error(WebApiResponse.ResponseMsg.FAIL.getValue());
    }
}
