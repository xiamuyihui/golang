package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.common.CommonUtils;
import com.kingthy.common.DateHelper;
import com.kingthy.dto.*;
import com.kingthy.dubbo.docking.service.IfoOrderInfoDubboService;
import com.kingthy.dubbo.goods.service.GoodsDubboService;
import com.kingthy.dubbo.opus.service.OpusDubboService;
import com.kingthy.dubbo.order.service.OrderDubboService;
import com.kingthy.dubbo.order.service.OrderItemDubboService;
import com.kingthy.dubbo.order.service.SnDubboService;
import com.kingthy.dubbo.user.service.MemberDubboService;
import com.kingthy.entity.*;
import com.kingthy.request.IfoCreateOrderReq;
import com.kingthy.request.IfoFullOrderReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.IfoOrderInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 14:39 on 2017/9/25.
 * @Modified by:
 */
@Service
public class IfoOrderInfoServiceImpl implements IfoOrderInfoService
{
    private static final String UPDATER_ID = CommonUtils.updaterId;

    @Reference(version = "1.0.0")
    private IfoOrderInfoDubboService ifoOrderInfoDubboService;

    @Reference(version = "1.0.0")
    private OrderDubboService orderDubboService;

    @Reference(version = "1.0.0")
    private GoodsDubboService goodsDubboService;

    @Reference(version = "1.0.0")
    private MemberDubboService memberDubboService;

    @Reference(version = "1.0.0")
    private OrderItemDubboService orderItemDubboService;

    @Reference(version = "1.0.0")
    private OpusDubboService opusDubboService;

    @Reference(version = "1.0.0")
    private SnDubboService snDubboService;

    @Override
    public WebApiResponse<?> createIfoOrder(IfoOrderDTO ifoOrderDTO) throws Exception {

        //判断参数不能为空
        for (Field f : ifoOrderDTO.getClass().getDeclaredFields()){
            f.setAccessible(true);
            if (StringUtils.isEmpty(f.get(ifoOrderDTO))){
                return WebApiResponse.error(WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
            }
        }

        if (StringUtils.isEmpty(ifoOrderDTO.getOrderSn())){
            return WebApiResponse.error(WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
        }

        Orders q = new Orders();
        q.setSn(ifoOrderDTO.getOrderSn());
        q.setDelFlag(false);
        Orders t = orderDubboService.selectOne(q);

        if (t == null){
            return WebApiResponse.error(WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
        }

        //查询子订单是否支付
        Long count = orderDubboService.selectOrdersCountByOrderItemSn(ifoOrderDTO.getOrderItemDTO().get(0).getOrderItemSn());

        if (count == null || count <= 0){
//            return WebApiResponse.error("订单状态不正确");
        }

        IfoOrderInfo var = new IfoOrderInfo();

        var.setOrderSn(t.getSn());
        //测试随机生成主订单号
//        var.setOrderSn(snDubboService.generateSn("order").getData());

        var.setMemberName(t.getMemberName());
        var.setCreateOrderDate(t.getCreateDate());
        //承诺交期
        var.setDelivery(DateHelper.addDay(new Date(), 7));
        //总金额
        var.setAmount(t.getAmount());
        //运费
        var.setFreight(t.getFreight());
        //是否要发票
        var.setIsInvoice(t.getIsInvoice());
        var.setInvoiceType(t.getInvoiceType());
        var.setInvoiceTitle(t.getInvoiceTitle());
        var.setPaymentMethodType(t.getPaymentMethodType());
        var.setPaymentStatus(true);
        var.setPhone(t.getPhone());
        String address = t.getProvinceName() + t.getCityName() + t.getAreaName() + t.getAddress();
        //包括省市区详细地址
        var.setAddress(address.replace("null", ""));
        var.setProvinceUuid(t.getProvinceUuid());
        var.setProvinceName(t.getProvinceName());
        var.setCityUuid(t.getCityUuid());
        var.setCityName(t.getCityName());
        var.setAreaUuid(t.getAreaUuid());
        var.setAreaName(t.getAreaName());
        var.setDetailAddress(t.getAddress());
        var.setZipCode(t.getZipCode());
        var.setConsignee(t.getConsignee());
        var.setMemo(t.getMemo());
        var.setCreateDate(t.getCreateDate());
        var.setUpdateId(UPDATER_ID);
        var.setOperSt(IfoOrderInfo.OperStType.add.getValue());
        var.setIfStatus(IfoOrderInfo.StatusType.init.getValue());

        List<IfoOrderInfoDetail> ifoOrderInfoDetailList = createOrderItem(ifoOrderDTO.getOrderItemDTO(), var.getOrderSn());

        if (ifoOrderInfoDetailList.isEmpty()){
            return WebApiResponse.error("子订单不正确");
        }

        IfoCreateOrderReq req = new IfoCreateOrderReq();
        req.setIfoOrderInfo(var);
        req.setDetailList(ifoOrderInfoDetailList);

        int result = ifoOrderInfoDubboService.createIfoOrder(req);

        return result > 0 ? WebApiResponse.success(WebApiResponse.ResponseMsg.SUCCESS.getValue())
                : WebApiResponse.error(WebApiResponse.ResponseMsg.FAIL.getValue());
    }

    private IfoOrderInfo mergeOrder(String orderSn){

        Orders q = new Orders();
        q.setSn(orderSn);
        q.setDelFlag(false);
        Orders t = orderDubboService.selectOne(q);

        IfoOrderInfo var = new IfoOrderInfo();

        var.setOrderSn(t.getSn());
        //测试随机生成主订单号
//        var.setOrderSn(snDubboService.generateSn("order").getData());

        var.setMemberName(t.getMemberName());
        var.setCreateOrderDate(t.getCreateDate());
        //承诺交期
        var.setDelivery(DateHelper.addDay(new Date(), 7));
        //总金额
        var.setAmount(t.getAmount());
        //运费
        var.setFreight(t.getFreight());
        //是否要发票
        var.setIsInvoice(t.getIsInvoice());
        var.setInvoiceType(t.getInvoiceType());
        var.setInvoiceTitle(t.getInvoiceTitle());
        var.setPaymentMethodType(t.getPaymentMethodType());
        var.setPaymentStatus(true);
        var.setPhone(t.getPhone());
        String address = t.getProvinceName() + t.getCityName() + t.getAreaName() + t.getAddress();
        //包括省市区详细地址
        var.setAddress(address.replace("null", ""));
        var.setProvinceUuid(t.getProvinceUuid());
        var.setProvinceName(t.getProvinceName());
        var.setCityUuid(t.getCityUuid());
        var.setCityName(t.getCityName());
        var.setAreaUuid(t.getAreaUuid());
        var.setAreaName(t.getAreaName());
        var.setDetailAddress(t.getAddress());
        var.setZipCode(t.getZipCode());
        var.setConsignee(t.getConsignee());
        var.setMemo(t.getMemo());
        var.setCreateDate(t.getCreateDate());
        var.setUpdateId(UPDATER_ID);
        var.setOperSt(IfoOrderInfo.OperStType.add.getValue());
        var.setIfStatus(IfoOrderInfo.StatusType.init.getValue());

        return var;

    }

    private IfoOrderInfoDetail mergeSubOrder(IfoFullOrderItemDTO ifoFullOrderItemDTO){

//        IfoOrderItemDTO ifoOrderItemDTO = map.get(item.getSn());

        OrderItem var = new OrderItem();

        var.setSn(ifoFullOrderItemDTO.getOrderInfo().getOrderItemSn());

        OrderItem item = orderItemDubboService.selectOne(var);

        IfoOrderInfoDetail t = new IfoOrderInfoDetail();

        t.setOrderSn(ifoFullOrderItemDTO.getOrderInfo().getOrderSn());

        t.setOrderItemSn(ifoFullOrderItemDTO.getOrderInfo().getOrderItemSn());
        t.setQuantity(item.getQuantity());
        t.setPrice(new BigDecimal(ifoFullOrderItemDTO.getOrderInfo().getPrice()));

        t.setStyleCode(ifoFullOrderItemDTO.getOrderInfo().getStyleCode());
        t.setStyleName(ifoFullOrderItemDTO.getOrderInfo().getStyleName());

        t.setStyleTypeCode(ifoFullOrderItemDTO.getOrderInfo().getStyleTypeCode());
        t.setStyleTypeName(ifoFullOrderItemDTO.getOrderInfo().getStyleTypeName());
        t.setStFlag(ifoFullOrderItemDTO.getOrderInfo().getStFlag() ? "Y" : "N");

        t.setStCodes(ifoFullOrderItemDTO.getOrderInfo().getStCodes());

        t.setFilePath(ifoFullOrderItemDTO.getOrderInfo().getFilePath().stream().collect(Collectors.joining(",")));
        t.setSyFilePath(ifoFullOrderItemDTO.getOrderInfo().getSyFilePath());

        Goods goods = goodsDubboService.selectByUuid(item.getProductUuid());

        t.setDesignerCode(goods.getDesinger());

        Member member = memberDubboService.selectByUuid(goods.getDesinger());

        if(member != null){
            t.setDesignerName(member.getUserName());
        }

        t.setCreateTime(new Date());
        t.setUpdateId(CommonUtils.updaterId);
        t.setOperSt(IfoOrderInfoDetail.OperStType.add.getValue());
        t.setIfStatus(IfoOrderInfoDetail.StatusType.init.getValue());

        return t;

    }

    /**
     * 一个接口创建所有订单信息
     * @param ifoFullOrderDTO
     * @return
     * @throws Exception
     */
    @Override
    public WebApiResponse<?> createFullIfoOrder(IfoFullOrderDTO ifoFullOrderDTO) throws Exception {


        String orderSn = orderDubboService.selectSnCountByOrderItemSn(ifoFullOrderDTO.getItemDTOList().get(0).getOrderInfo().getOrderItemSn());

        if (StringUtils.isEmpty(orderSn)){
            return WebApiResponse.error(WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
        }

        //bom信息
        List<IfoOrderDetailBom> bomList = new ArrayList<>();
        //款式文件信息
        List<IfoOrderStyleFileInfo> styleFileList = new ArrayList<>();
        //缝合关系信息
        List<IfoOrderStitchingInfo> stitchingStyles = new ArrayList<>();

        //主订单
        IfoOrderInfo ifoOrderInfo = null;

        //子订单
        List<IfoOrderInfoDetail> ifoOrderInfoDetails = new ArrayList<>();

        for (IfoFullOrderItemDTO dto : ifoFullOrderDTO.getItemDTOList()){

            dto.getOrderInfo().setOrderSn(orderSn);
            //查询子订单是否 是支付成功的状态
            Long count = orderDubboService.selectOrdersCountByOrderItemSn(dto.getOrderInfo().getOrderItemSn());

            if (count == null || count <= 0){
               // return WebApiResponse.error("订单状态不正确");
            }

            //只有一个主订单
            if (ifoOrderInfo == null){
                ifoOrderInfo = mergeOrder(dto.getOrderInfo().getOrderSn());
            }


            ifoOrderInfoDetails.add(mergeSubOrder(dto));

            //bom信息
            for (IfoOrderDetailBomDTO.BomInfo bomInfo : dto.getBomInfoList()){

                IfoOrderDetailBom t = new IfoOrderDetailBom();
                t.setOrderItemSn(dto.getOrderInfo().getOrderItemSn());
                t.setStyleCode(bomInfo.getStyleCode());
                t.setComponentTypeCode(bomInfo.getComponentTypeCode());
                t.setComponentTypeName(bomInfo.getComponentTypeName());
                t.setPartsCode(bomInfo.getPartsCode());
                t.setPartsName(bomInfo.getPartsName());
//                t.setInstanceType(bomInfo.getMaterialTypeCode());
                t.setInstanceType(bomInfo.getInstanceType());
                t.setMaterialCode(bomInfo.getMaterialCode());
                t.setMaterialName(bomInfo.getMaterialName());
                t.setQuantity(bomInfo.getQuantity());
                t.setUnit(bomInfo.getUnit());
                t.setPiecesCode(bomInfo.getPiecesCode());
                t.setPiecesName(bomInfo.getPiecesName());
                t.setPiecesPath(bomInfo.getPiecesPath());
                t.setFactFlag(bomInfo.getFactFlag());
                t.setPartsPatternPath(bomInfo.getPartsPatternPath());
                t.setPartsPath(bomInfo.getPartsPath());

                t.setCreateTime(new Date());
                t.setUpdateId(CommonUtils.updaterId);
                t.setOperSt(IfoOrderDetailBom.OperStType.add.getValue());
                t.setIfStatus(IfoOrderDetailBom.StatusType.init.getValue());

                bomList.add(t);
            }

            //款式文件信息
            for (IfoOrderStyleFileDTO.StyleInfo styleInfo : dto.getStyleInfoList()){

                IfoOrderStyleFileInfo t = new IfoOrderStyleFileInfo();
                t.setOrderItemSn(dto.getOrderInfo().getOrderItemSn());
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

            //缝合关系
            for (IfoStitchingStyleDTO.StitchingInfo stitchingInfo : dto.getStitchingInfoList()){

                IfoOrderStitchingInfo t = new IfoOrderStitchingInfo();
                t.setStitchingCode(stitchingInfo.getStitchingCode());
                t.setStitchingDesc(stitchingInfo.getStitchingDesc());
                t.setPiecesCodes(stitchingInfo.getPiecesCodes());
                t.setPartsCodes(stitchingInfo.getPartsCodes());
                t.setStandardCodes(stitchingInfo.getStandardCodes());
                t.setOrderItemSn(dto.getOrderInfo().getOrderItemSn());
                t.setCreateTime(new Date());
                t.setUpdateId(CommonUtils.updaterId);
                t.setOperSt(IfoOrderStitchingInfo.OperStType.add.getValue());
                t.setIfStatus(IfoOrderStitchingInfo.StatusType.init.getValue());

                stitchingStyles.add(t);
            }
        }

        IfoFullOrderReq ifoFullOrderReq = new IfoFullOrderReq();
        ifoFullOrderReq.setBomList(bomList);
        ifoFullOrderReq.setStyleFileList(styleFileList);
        ifoFullOrderReq.setStitchingStyles(stitchingStyles);
        ifoFullOrderReq.setIfoOrderInfoDetails(ifoOrderInfoDetails);
        ifoFullOrderReq.setIfoOrderInfo(ifoOrderInfo);

        return ifoOrderInfoDubboService.createFullIfoOrder(ifoFullOrderReq) > 0 ?
                WebApiResponse.success() : WebApiResponse.error("创建所有接口信息失败");
    }

    //测试订单号
//    private String testItemOrderSn = "20170922504382551";

    @Override
    public WebApiResponse<?> createIfoOrder(IfoOrderItemDTO ifoOrderItemDTO) throws Exception {

//        String orderSn = orderDubboService.selectSnCountByOrderItemSn(testItemOrderSn);

        String orderSn = orderDubboService.selectSnCountByOrderItemSn(ifoOrderItemDTO.getOrderItemSn());

        ifoOrderItemDTO.setOrderSn(orderSn);

        IfoOrderDTO ifoOrderDTO = new IfoOrderDTO();

        ifoOrderDTO.setOrderItemDTO(Arrays.asList(ifoOrderItemDTO));

        ifoOrderDTO.setOrderSn(orderSn);
        ifoOrderDTO.setToken(ifoOrderItemDTO.getToken());
        ifoOrderDTO.setUserUuid(ifoOrderItemDTO.getUserUuid());

        return createIfoOrder(ifoOrderDTO);
    }

    @Override
    public String getSn(String type) {
        return snDubboService.generateSn(type).getData();
    }

    /**
     * 子订单
     * @param ifoOrderItemDTOList
     * @param orderSn
     * @return
     * @throws Exception
     */
    private List<IfoOrderInfoDetail> createOrderItem(List<IfoOrderItemDTO> ifoOrderItemDTOList, String orderSn) throws Exception{

        List<String> itemSnList = new ArrayList<>();
        Map<String, IfoOrderItemDTO> map = new HashMap<>(ifoOrderItemDTOList.size());
        for (IfoOrderItemDTO dto : ifoOrderItemDTOList){

            if (!StringUtils.isEmpty(dto.getOrderItemSn())){
                itemSnList.add(dto.getOrderItemSn());
                map.put(dto.getOrderItemSn(), dto);
            }
        }

        List<OrderItem> orderItems = orderItemDubboService.selectOrderItemList(itemSnList);
//        List<OrderItem> orderItems = orderItemDubboService.selectOrderItemList(Arrays.asList(testItemOrderSn));

        List<IfoOrderInfoDetail> ifoOrderInfoDetailList = new ArrayList<>();

        for (OrderItem item : orderItems){
            if (item != null
                    && map.containsKey(item.getSn())
                    ){

                IfoOrderItemDTO ifoOrderItemDTO = map.get(item.getSn());
//                IfoOrderItemDTO ifoOrderItemDTO = ifoOrderItemDTOList.get(0);

                IfoOrderInfoDetail t = new IfoOrderInfoDetail();

//                t.setOrderItemSn(snDubboService.generateSn("order").getData());
                t.setOrderSn(orderSn);

                t.setOrderItemSn(ifoOrderItemDTO.getOrderItemSn());
//                t.setOrderSn(ifoOrderItemDTO.getOrderSn());

                t.setQuantity(item.getQuantity());
                t.setPrice(new BigDecimal(ifoOrderItemDTO.getPrice()));

                t.setStyleCode(ifoOrderItemDTO.getStyleCode());
//                t.setStyleCode(snDubboService.generateSn("opus").getData());
                t.setStyleName(ifoOrderItemDTO.getStyleName());

                t.setStyleTypeCode(ifoOrderItemDTO.getStyleTypeCode());
                t.setStyleTypeName(ifoOrderItemDTO.getStyleTypeName());
                t.setStFlag(ifoOrderItemDTO.getStFlag() ? "Y" : "N");

                t.setStCodes(ifoOrderItemDTO.getStCodes());

                t.setFilePath(ifoOrderItemDTO.getFilePath().stream().collect(Collectors.joining(",")));
                t.setSyFilePath(ifoOrderItemDTO.getSyFilePath());

                Goods goods = goodsDubboService.selectByUuid(item.getProductUuid());

                t.setDesignerCode(goods.getDesinger());

                /*
                Opus opus = opusDubboService.selectByUuid(goods.getOpusUuid());

                if (opus != null){
//                    t.setStyleCode(opus.getSn());
//                    t.setStyleName(opus.getOpusName());
                }*/

                Member member = memberDubboService.selectByUuid(goods.getDesinger());

                if(member != null){
                    t.setDesignerName(member.getUserName());
                }

                t.setCreateTime(new Date());
                t.setUpdateId(CommonUtils.updaterId);
                t.setOperSt(IfoOrderInfoDetail.OperStType.add.getValue());
                t.setIfStatus(IfoOrderInfoDetail.StatusType.init.getValue());

                ifoOrderInfoDetailList.add(t);
            }
        }

        return ifoOrderInfoDetailList;
    }

/*    private String stCodeArrayToString(List<String> stcodes){

        if (stcodes != null && stcodes.size() > 0){

            StringBuffer stringBuffer = new StringBuffer();

            stcodes.forEach(st -> {
                stringBuffer.append(st);
                stringBuffer.append(";;;qqq");
            });

            return stringBuffer.substring(0, stringBuffer.lastIndexOf(";;;qqq"));
        }

        return null;

    }*/
}
