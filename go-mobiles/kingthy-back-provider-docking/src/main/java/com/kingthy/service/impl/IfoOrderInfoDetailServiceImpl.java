package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.kingthy.common.CommonUtils;
import com.kingthy.dto.IfoOrderItemDTO;
import com.kingthy.dubbo.docking.service.IfoOrderInfoDetailDubboService;
import com.kingthy.dubbo.goods.service.GoodsDubboService;
import com.kingthy.dubbo.order.service.OrderItemDubboService;
import com.kingthy.dubbo.user.service.MemberDubboService;
import com.kingthy.entity.Goods;
import com.kingthy.entity.IfoOrderInfoDetail;
import com.kingthy.entity.Member;
import com.kingthy.entity.OrderItem;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.IfoOrderInfoDetailService;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.stream.Collectors;


/**
 * @author xumin
 * @Description:子订单
 * @DATE Created by 19:14 on 2017/9/25.
 * @Modified by:
 */

@Service
public class IfoOrderInfoDetailServiceImpl implements IfoOrderInfoDetailService
{
    @Reference(version = "1.0.0", timeout = 3000)
    private IfoOrderInfoDetailDubboService ifoOrderInfoDetailDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private OrderItemDubboService orderItemDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private GoodsDubboService goodsDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private MemberDubboService memberDubboService;

    @Override
    public WebApiResponse<?> createOrderItem(IfoOrderItemDTO ifoOrderItemDTO) throws Exception {

        OrderItem var1 = new OrderItem();
        var1.setSn(ifoOrderItemDTO.getOrderItemSn());
        OrderItem item = orderItemDubboService.selectOne(var1);

        if (item == null){
            return WebApiResponse.error(WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
        }

        IfoOrderInfoDetail t = new IfoOrderInfoDetail();

        t.setOrderItemSn(ifoOrderItemDTO.getOrderItemSn());
        t.setOrderSn(ifoOrderItemDTO.getOrderSn());
        t.setQuantity(item.getQuantity());
        t.setPrice(item.getPrice());
        t.setStyleCode(ifoOrderItemDTO.getStyleCode());
        t.setStyleName(ifoOrderItemDTO.getStyleName());
        t.setStyleTypeCode(ifoOrderItemDTO.getStyleTypeCode());
        t.setStyleTypeName(ifoOrderItemDTO.getStyleTypeName());
        t.setStFlag(ifoOrderItemDTO.getStFlag() ? "Y" : "N");

        t.setStCodes(ifoOrderItemDTO.getStCodes());

        t.setFilePath(ifoOrderItemDTO.getFilePath().stream().collect(Collectors.joining(",")));

        Goods goods = goodsDubboService.selectByUuid(item.getProductUuid());

        t.setDesignerCode(goods.getDesinger());

        Member member = memberDubboService.selectByUuid(goods.getDesinger());

        t.setDesignerName(member.getUserName());

        t.setCreateTime(new Date());
        t.setUpdateId(CommonUtils.updaterId);
        t.setOperSt(IfoOrderInfoDetail.OperStType.add.getValue());
        t.setIfStatus(IfoOrderInfoDetail.StatusType.init.getValue());

        int result = ifoOrderInfoDetailDubboService.insert(t);


        return result > 0 ? WebApiResponse.success(WebApiResponse.ResponseMsg.SUCCESS.getValue())
                : WebApiResponse.error(WebApiResponse.ResponseMsg.FAIL.getValue());
    }
}
