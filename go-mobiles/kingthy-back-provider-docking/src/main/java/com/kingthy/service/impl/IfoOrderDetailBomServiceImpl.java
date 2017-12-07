package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.common.CommonUtils;
import com.kingthy.dto.IfoOrderDetailBomDTO;
import com.kingthy.dubbo.docking.service.IfoOrderDetailBomDubboService;
import com.kingthy.dubbo.order.service.OrderDubboService;
import com.kingthy.dubbo.order.service.OrderItemDubboService;
import com.kingthy.entity.IfoOrderDetailBom;
import com.kingthy.entity.OrderItem;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.IfoOrderDetailBomService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author xumin
 * @Description:以面料，里布，朴布，辅料为维度的bom清单
 * @DATE Created by 20:27 on 2017/9/25.
 * @Modified by:
 */

@Service
public class IfoOrderDetailBomServiceImpl implements IfoOrderDetailBomService
{

    @Reference(version = "1.0.0", timeout = 3000)
    private IfoOrderDetailBomDubboService ifoOrderDetailBomDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private OrderDubboService orderDubboService;

    @Override
    public WebApiResponse<?> createOrderItemBom(IfoOrderDetailBomDTO detailBomDTO) throws Exception {

        if (detailBomDTO == null || detailBomDTO.getBomInfoList() == null
                || detailBomDTO.getBomInfoList().isEmpty() || StringUtils.isEmpty(detailBomDTO.getOrderItemSn()))
        {
            return WebApiResponse.error(WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
        }

        //查询子订单是否 是支付成功的状态
        Long count = orderDubboService.selectOrdersCountByOrderItemSn(detailBomDTO.getOrderItemSn());

        if (count == null || count <= 0){
//            return WebApiResponse.error("订单状态不正确");
        }

        List<IfoOrderDetailBom> bomList = new ArrayList<>();

        for (IfoOrderDetailBomDTO.BomInfo boomInfo : detailBomDTO.getBomInfoList())
        {

            IfoOrderDetailBom t = new IfoOrderDetailBom();
            t.setOrderItemSn(detailBomDTO.getOrderItemSn());
            t.setStyleCode(boomInfo.getStyleCode());
            t.setComponentTypeCode(boomInfo.getComponentTypeCode());
            t.setComponentTypeName(boomInfo.getComponentTypeName());
            t.setPartsCode(boomInfo.getPartsCode());
            t.setPartsName(boomInfo.getPartsName());
//            t.setInstanceType(boomInfo.getMaterialTypeCode());
            t.setInstanceType(boomInfo.getInstanceType());
            t.setMaterialCode(boomInfo.getMaterialCode());
            t.setMaterialName(boomInfo.getMaterialName());
            t.setQuantity(boomInfo.getQuantity());
            t.setUnit(boomInfo.getUnit());
            t.setPiecesCode(boomInfo.getPiecesCode());
            t.setPiecesName(boomInfo.getPiecesName());
            t.setPiecesPath(boomInfo.getPiecesPath());
            t.setFactFlag(boomInfo.getFactFlag());
            t.setPartsPatternPath(boomInfo.getPartsPatternPath());
            t.setPartsPath(boomInfo.getPartsPath());

            t.setCreateTime(new Date());
            t.setUpdateId(CommonUtils.updaterId);
            t.setOperSt(IfoOrderDetailBom.OperStType.add.getValue());
            t.setIfStatus(IfoOrderDetailBom.StatusType.init.getValue());

            bomList.add(t);
        }

        int result = ifoOrderDetailBomDubboService.insertList(bomList);

        return result > 0 ? WebApiResponse.success(WebApiResponse.ResponseMsg.SUCCESS.getValue())
                : WebApiResponse.error(WebApiResponse.ResponseMsg.FAIL.getValue());
    }

}
