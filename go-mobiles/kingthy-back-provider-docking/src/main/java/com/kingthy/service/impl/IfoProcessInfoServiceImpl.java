package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.common.CommonUtils;
import com.kingthy.dto.IfoProcessInfoDTO;
import com.kingthy.dubbo.docking.service.IfoProcessInfoDuubboService;
import com.kingthy.dubbo.order.service.OrderDubboService;
import com.kingthy.entity.IfoProcessInfo;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.IfoProcessInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xumin
 * @Description:缝制工艺工序接口
 * @DATE Created by 14:49 on 2017/9/26.
 * @Modified by:
 */
@Service
public class IfoProcessInfoServiceImpl implements IfoProcessInfoService
{

    @Reference(version = "1.0.0", timeout = 3000)
    private IfoProcessInfoDuubboService ifoProcessInfoDuubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private OrderDubboService orderDubboService;

    @Override
    public WebApiResponse<?> createIfoProcess(IfoProcessInfoDTO ifoProcessInfoDTO) throws Exception {

        if (ifoProcessInfoDTO == null || ifoProcessInfoDTO.getProcessInfoList() == null
                || ifoProcessInfoDTO.getProcessInfoList().isEmpty() || StringUtils.isEmpty(ifoProcessInfoDTO.getOrderItemSn()))
        {
            return WebApiResponse.error(WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
        }

        //查询子订单是否 是支付成功的状态
        Long count = orderDubboService.selectOrdersCountByOrderItemSn(ifoProcessInfoDTO.getOrderItemSn());

        if (count == null || count <= 0){
//            return WebApiResponse.error("订单状态不正确");
        }

        List<IfoProcessInfo> processInfoList = new ArrayList<>();

        for (IfoProcessInfoDTO.ProcessInfo processInfo : ifoProcessInfoDTO.getProcessInfoList()){

            IfoProcessInfo t = new IfoProcessInfo();
            t.setOrderItemSn(ifoProcessInfoDTO.getOrderItemSn());
            t.setStyleCode(processInfo.getStyleCode());
            t.setStyleTypeCode(processInfo.getStyleTypeCode());
            t.setProcessMemo(processInfo.getProcessMemo());
            t.setProcessNo(processInfo.getProcessNo());
            t.setEquipmentType(processInfo.getEquipmentType());
            t.setComponentTypeCode(processInfo.getComponentTypeCode());
            t.setPartsTypeCode(processInfo.getPartsTypeCode());
            t.setStitchingCodes(processInfo.getStitchingCodes());
            t.setPiecesCodes(processInfo.getPiecesCodes());

            t.setCreateTime(new Date());
            t.setUpdateId(CommonUtils.updaterId);
            t.setOperSt(IfoProcessInfo.OperStType.add.getValue());
            t.setStatus(IfoProcessInfo.StatusType.init.getValue());

            processInfoList.add(t);
        }

        int result = ifoProcessInfoDuubboService.insertList(processInfoList);

        return result > 0 ? WebApiResponse.success(WebApiResponse.ResponseMsg.SUCCESS.getValue())
                : WebApiResponse.error(WebApiResponse.ResponseMsg.FAIL.getValue());
    }
}
