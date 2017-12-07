package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.cache.RedisManager;
import com.kingthy.common.DateHelper;
import com.kingthy.constant.CacheMqNameConstans;
import com.kingthy.constant.RabbitmqConstants;
import com.kingthy.dto.*;
import com.kingthy.dubbo.basedata.service.AreaDubboService;
import com.kingthy.dubbo.order.service.OrderDubboService;
import com.kingthy.dubbo.order.service.SnDubboService;
import com.kingthy.dubbo.user.service.MemberDubboService;
import com.kingthy.entity.Member;
import com.kingthy.entity.OrderItem;
import com.kingthy.entity.Orders;
import com.kingthy.exception.BizException;
import com.kingthy.request.CreateOrderReq;
import com.kingthy.response.CreateOrderResponse;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.OrderCreateService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 15:45 on 2017/11/14.
 * @Modified by:
 */
@Service
public class OrderCreateServiceImpl implements OrderCreateService
{

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCreateServiceImpl.class);

    @Reference(version = "1.0.0")
    private MemberDubboService memberDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private OrderDubboService orderDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private SnDubboService snDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private AreaDubboService areaService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisManager redisManager;

    public void create(OrderCreateMqDTO message) throws Exception{

        Orders orders = mergeOrders(message.getPrice(), message.getMemberUuid(), message.getOrderDTO(), message.getQuantity(), message.getOrderSn());

        Map<String, OrderCreateDTO> mapGoods = new HashedMap();


        message.getOrderDTO().getGoods().forEach(c -> mapGoods.put(c.getGoodsUuId(), c));

        //创建子订单
        List<OrderItem> orderItemList = crateSubOrder(message.getMemberUuid(), mapGoods, message.getGoodsOrderDTOList(), message.getOrderDTO());

        CreateOrderReq var = new CreateOrderReq();
        var.setOrders(orders);
        var.setOrderItemList(orderItemList);

        CreateOrderResponse response = orderDubboService.createOrder(var);

        if (StringUtils.isEmpty(response.getOrdersSn())){

            throw new BizException("创建订单失败");
        }

        orderPushRabbitmq(response.getListOrderItemSn(), orderItemList);
    }
    /**
     * 创建子订单
     * @param creator
     * @param goodsOrderDTOList
     */
    private List<OrderItem> crateSubOrder(String creator, Map<String, OrderCreateDTO> mapGoods,
                                          List<GoodsOrderDTO> goodsOrderDTOList, OrderDTO orderDTO){

        List<OrderItem> orderItemList = new ArrayList<>();

        //子订单信息
        for (GoodsOrderDTO dto : goodsOrderDTOList){

            //封装子订单数据
            OrderItem orderItem = new OrderItem();
            orderItem.setCreateDate(new Date());
            orderItem.setCreator(creator);
            orderItem.setModifyDate(new Date());
            orderItem.setVersion(0L);
            orderItem.setIsDelivery(true);
            orderItem.setName(dto.getGoodsName());
            orderItem.setPrice(new BigDecimal(dto.getStandardPrice()));
            orderItem.setQuantity(dto.getQuantity());

            String sn = getSn();

            if(StringUtils.isEmpty(sn)){
                throw new BizException("创建订单号失败");
            }

            orderItem.setSn(sn);
            orderItem.setType(Orders.OrderType.general.getValue());
            orderItem.setProductUuid(dto.getGoodsUuid());
            orderItem.setDelFlag(false);
            orderItem.setRefundAmount(new BigDecimal(0));
            orderItem.setReturnedQuantity(0);
            orderItem.setShippedQuantity(0);

            orderItem.setPhone(orderDTO.getPhone());
            orderItem.setStatus(Orders.OrderType.general.getValue());
            orderItem.setMemberName(queryMemberName(creator));
            orderItem.setMemberUuid(creator);

            if (mapGoods.get(dto.getGoodsUuid()) != null){
                OrderCreateDTO tmp = mapGoods.get(dto.getGoodsUuid());
                orderItem.setMemo(tmp.getMemo());
                orderItem.setFigureUuid(tmp.getFigureUuid());
                //配送方式
                orderItem.setShippingMethodUuid(orderDTO.getShippingMethodUuid());
                orderItem.setShippingMethodName(orderDTO.getShippingMethodName());

                //发票信息
                orderItem.setIsInvoice(orderDTO.getIsInvoice() == null ? false : true);
                orderItem.setInvoiceTitle(orderDTO.getInvoiceTitle());
                orderItem.setInvoiceType(orderDTO.getInvoiceType());
            }

            Double amount = dto.getQuantity() * dto.getStandardPrice();

            orderItem.setAmount(new BigDecimal(amount));

            orderItemList.add(orderItem);
        }

        return orderItemList;
    }

    private Orders mergeOrders(Double price, String memberUuid, OrderDTO orderDTO, Integer quantity, String sn)
    {

        //订单金额
        Double amount = price;

        Orders order = new Orders();
        order.setCreateDate(new Date());

        String creator = memberUuid;
        order.setCreator(creator);
        order.setModifyDate(new Date());
        order.setVersion(0);

        order.setAddress(orderDTO.getAddress());
        order.setAmount(new BigDecimal(amount));
        order.setAreaName(orderDTO.getAreaName());
        order.setAreaUuid(orderDTO.getAreaUuid());

        //根据县区ID查询 省 市 ID 名称
        if (orderDTO.getAreaUuid() != null){

            CityInfoDTO cityInfoDTO = areaService.selectCityInfoByAreaId(orderDTO.getAreaUuid());

            if (cityInfoDTO != null){
                order.setCityUuid(cityInfoDTO.getCityUuid());
                order.setCityName(cityInfoDTO.getCityName());
                order.setProvinceUuid(cityInfoDTO.getProvinceUuid());
                order.setProvinceName(cityInfoDTO.getProvinceName());
            }
        }

        order.setConsignee(orderDTO.getConsignee());
        order.setCouponDiscount(new BigDecimal(0));
        order.setExchangePoint(0);
        order.setExpire(DateHelper.addHour(new Date(), 2));

        order.setFee(new BigDecimal(0));
        order.setFreight(new BigDecimal(0));

        order.setIsExchangePoint(false);
        order.setIsUseCouponCode(false);

        order.setOffsetAmount(new BigDecimal(0));
        order.setPhone(orderDTO.getPhone());
        order.setPrice(new BigDecimal(price));
        order.setQuantity(quantity);

        order.setRefundAmount(new BigDecimal(0));
        order.setReturnedQuantity(0);
        order.setShippedQuantity(0);

        order.setSn(sn);
        order.setStatus(Orders.OrderType.general.getValue());
        order.setTax(new BigDecimal(0));
        order.setType(Orders.OrderStatus.pendingPayment.getValue());

        order.setMemberName(queryMemberName(memberUuid));
        order.setMemberUuid(memberUuid);

        order.setDelFlag(false);

        return order;
    }

    /**
     * 获取sn
     * @return
     */
    private String getSn(){

        WebApiResponse<String> sn = snDubboService.generateSn("order");
        return sn.getCode() != WebApiResponse.SUCCESS_CODE ? null : sn.getData();
    }

    private String queryMemberName(String memberUuid)
    {
        Member member = memberDubboService.selectByUuid(memberUuid);
        return member != null ? member.getUserName() : null;
    }

    private void orderPushRabbitmq(List<String> listOrderItemSn, List<OrderItem> orderItemList){

        listOrderItemSn.forEach(itemSn -> {
            try{

                //发送消息更新缓存
                String cacheKey= redisManager.generateCacheKey(SingleOrderDTO.class, itemSn);
                UpdateCacheQueueDTO queue = new UpdateCacheQueueDTO();
                queue.setCacheKey(cacheKey);
                rabbitTemplate.convertAndSend(CacheMqNameConstans.EXCHANGE_NAME,CacheMqNameConstans.ROUTING_KEY_NAME, queue);
            }catch (Exception e){
                LOGGER.error("发送rabbitmq.失败 订单号 : "+itemSn+" : " + e);
            }
        });

        orderItemList.forEach(orderItem -> {

            try{

                //发送消息更新销量
                EsGoodsRabbitDTO goodsRabbitDTO = new EsGoodsRabbitDTO();
                goodsRabbitDTO.setGoodsUuid(orderItem.getProductUuid());
                goodsRabbitDTO.setCreateDate(new Date());
                rabbitTemplate.convertAndSend(RabbitmqConstants.EsGoodsEnum.EXCHANGE.getValue(),
                        RabbitmqConstants.EsGoodsEnum.ROUTING.getValue(), goodsRabbitDTO);

            }catch (Exception e){
                LOGGER.error("发送rabbitmq.失败 商品UUID : "+orderItem.getProductUuid()+" : " + e);
            }

        });
    }
}
