package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.common.CommonUtils;
import com.kingthy.dto.IfoOrderStyleFileDTO;
import com.kingthy.dubbo.docking.service.IfoOrderStyleFileDubboService;
import com.kingthy.dubbo.order.service.OrderDubboService;
import com.kingthy.entity.IfoOrderStyleFileInfo;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.IfoOrderStyleFileService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xumin
 * @Description: 款式文件接口表
 * @DATE Created by 11:52 on 2017/9/26.
 * @Modified by:
 */

@Service
public class IfoOrderStyleFileServiceImpl implements IfoOrderStyleFileService
{

    @Reference(version = "1.0.0", timeout = 3000)
    private IfoOrderStyleFileDubboService ifoOrderStyleFileDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private OrderDubboService orderDubboService;

    @Override
    public WebApiResponse<?> createOrderStyleFile(IfoOrderStyleFileDTO ifoOrderStyleFileDTO) throws Exception {


        if (ifoOrderStyleFileDTO == null || ifoOrderStyleFileDTO.getStyleInfoList() == null
                || ifoOrderStyleFileDTO.getStyleInfoList().isEmpty() || StringUtils.isEmpty(ifoOrderStyleFileDTO.getOrderItemSn()))
        {
            return WebApiResponse.error(WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
        }

        //查询子订单是否 是支付成功的状态
        Long count = orderDubboService.selectOrdersCountByOrderItemSn(ifoOrderStyleFileDTO.getOrderItemSn());

        if (count == null || count <= 0){
//            return WebApiResponse.error("订单状态不正确");
        }

        List<IfoOrderStyleFileInfo> styleFileList = new ArrayList<>();

        for (IfoOrderStyleFileDTO.StyleInfo styleInfo : ifoOrderStyleFileDTO.getStyleInfoList()){

            IfoOrderStyleFileInfo t = new IfoOrderStyleFileInfo();
            t.setOrderItemSn(ifoOrderStyleFileDTO.getOrderItemSn());
            t.setStyleCode(styleInfo.getStyleCode());
            t.setMaterialCode(styleInfo.getMaterialCode());
            t.setBreadth(styleInfo.getBreadth());
            t.setQuantity(styleInfo.getQuantity());
            t.setUnit(styleInfo.getUnit());
            t.setAcreage(styleInfo.getAcreage());
            t.setPltPath(styleInfo.getPltPath());
            t.setNcPath(styleInfo.getNcPath());
            t.setCreateTime(new Date());
            t.setUpdateId(CommonUtils.updaterId);
            t.setOperSt(IfoOrderStyleFileInfo.OperStType.add.getValue());
            t.setIfStatus(IfoOrderStyleFileInfo.StatusType.init.getValue());

            styleFileList.add(t);
        }

        int result = ifoOrderStyleFileDubboService.insertList(styleFileList);

        return result > 0 ? WebApiResponse.success(WebApiResponse.ResponseMsg.SUCCESS.getValue())
                : WebApiResponse.error(WebApiResponse.ResponseMsg.FAIL.getValue());
    }
}
