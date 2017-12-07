package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.kingthy.cache.RedisManager;
import com.kingthy.common.CommonUtils;
import com.kingthy.constant.CacheMqNameConstans;
import com.kingthy.constant.GoodsContant;
import com.kingthy.constant.RabbitmqConstants;
import com.kingthy.dto.*;
import com.kingthy.dubbo.basedata.service.AreaDubboService;
import com.kingthy.dubbo.basedata.service.BaseDataDubboService;
import com.kingthy.dubbo.basedata.service.ShippingMethodDubboService;
import com.kingthy.dubbo.cart.service.CartDubboService;
import com.kingthy.dubbo.cart.service.CartItemDubboService;
import com.kingthy.dubbo.goods.service.GoodsDubboService;
import com.kingthy.dubbo.order.service.*;
import com.kingthy.dubbo.review.service.BuyersShowDubboService;
import com.kingthy.dubbo.review.service.BuyersShowImgDubboService;
import com.kingthy.dubbo.user.service.FigureDubboService;
import com.kingthy.dubbo.user.service.MemberDubboService;
import com.kingthy.entity.*;
import com.kingthy.response.AfterSaleServiceResp;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.GoodsService;
import com.kingthy.service.OrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 订单模块相关操作
 * 
 * @author 潘勇
 *
 */
@Service
@Transactional(isolation = Isolation.DEFAULT, rollbackFor = {java.lang.Exception.class})
public class OrderServiceImpl implements OrderService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Reference(version = "1.0.0", timeout = 3000)
    private OrderDubboService orderDubboService;


    @Reference(version = "1.0.0", timeout = 3000)
    private AreaDubboService areaService;


    @Reference(version = "1.0.0", timeout = 3000)
    private OrderLogDubboService orderLogDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private AfterSaleServiceDubboService afterSaleServiceDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private AfterSaleScheduleDubboService afterSaleScheduleDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private BaseDataDubboService baseDataService;

    @Reference(version = "1.0.0", timeout = 3000)
    private BuyersShowDubboService buyersShowService;

    @Reference(version = "1.0.0", timeout = 3000)
    private BuyersShowImgDubboService buyersShowImgService;

    @Reference(version = "1.0.0", timeout = 3000)
    private ShippingMethodDubboService shippingMethodDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private CartDubboService cartDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private CartItemDubboService cartItemDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private FigureDubboService figureDubboService;

    @Reference(version = "1.0.0", timeout = 3000)
    private GoodsDubboService goodsDubboService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Reference(version ="1.0.0")
    private SnDubboService snDubboService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisManager redisManager;

    @Reference(version = "1.0.0")
    private OrderItemDubboService orderItemDubboService;

    @Reference(version = "1.0.0")
    private MemberDubboService memberDubboService;

    @Reference(version = "1.0.0")
    private BuyersShowDubboService buyersShowDubboService;

    @Reference(version = "1.0.0")
    private BuyersShowImgDubboService buyersShowImgDubboService;

    private String getMember(String token)
    {
        return stringRedisTemplate.opsForValue().get(token);
    }

    /**
     * 获取sn
     * @return
     */
    private String getSn(){

        /*
        WebApiResponse<String> sn = snDubboService.generateSn("order");
        return sn.getCode() != WebApiResponse.SUCCESS_CODE ? null : sn.getData();
        */

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        String key = CommonUtils.REIDS_ORDER_SN_KEY + ":" + sdf.format(new Date());

        return stringRedisTemplate.opsForList().rightPop(key);
    }

    /**
     * 创建订单
     * @param orderDTO
     * @return
     * @throws Exception
     */

    @HystrixCommand(groupKey = "OrderGroup", commandKey = "CreateOrderCommand",fallbackMethod = "queryAllGoodsFallback",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
            },
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "2000"),
                    @HystrixProperty(name = "maxQueueSize", value = "100")
            })
    @Override
    public WebApiResponse<?> createOrder(OrderDTO orderDTO) throws Exception {

        if (orderDTO.getGoods() == null
                || orderDTO.getGoods().isEmpty()
                || StringUtils.isEmpty(orderDTO.getToken())
                || StringUtils.isEmpty(orderDTO.getUserUuid())){
            return WebApiResponse.error("参数不正确");
        }

        String memberUuid = getMember(orderDTO.getToken());


        if (StringUtils.isEmpty(memberUuid) || !memberUuid.equals(orderDTO.getUserUuid())){
            return WebApiResponse.error("token不正确");
        }


        Map<String, OrderCreateDTO> mapGoods = new HashedMap();

        List<String> listGoodsUuid = new ArrayList<>();

        for (OrderCreateDTO c : orderDTO.getGoods()){

            if (!NumberUtils.isDigits(c.getGoodsUuId())){
                return WebApiResponse.error(WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
            }

            mapGoods.put(c.getGoodsUuId(), c);
            listGoodsUuid.add(c.getGoodsUuId());
        }

        List<GoodsOrderDTO> goodsOrderDTOList = new ArrayList<>();

        for (String goodUuid : listGoodsUuid){

            Goods goods = goodsService.selectGoodsByUuid(goodUuid);

            if (goods == null){
                return WebApiResponse.error("查询不到商品");
            }

            GoodsOrderDTO goodsOrderDTO = new GoodsOrderDTO();
            goodsOrderDTO.setDelFlag(goods.getDelFlag());
            goodsOrderDTO.setGoodsName(goods.getGoodsName());
            goodsOrderDTO.setGoodsUuid(goods.getUuid());
            goodsOrderDTO.setMaterielInfo(goods.getMaterielInfo());
            goodsOrderDTO.setPartInfo(goods.getPartInfo());
            goodsOrderDTO.setStandardPrice(goods.getStandardPrice().doubleValue());
            goodsOrderDTO.setStatus(goods.getStatus());

            goodsOrderDTOList.add(goodsOrderDTO);
        }

        /*List<GoodsOrderDTO> goodsOrderDTOList = goodsDubboService.selectGoodsListByGoodsIds(listGoodsUuid);

        if (goodsOrderDTOList == null || goodsOrderDTOList.isEmpty()){
            return WebApiResponse.error("查询不到商品");
        }*/

        //商品价格
        Double price = 0d;

        //商品数量
        Integer quantity = 0;

        for (GoodsOrderDTO dto : goodsOrderDTOList){
            if (dto.getDelFlag()){
                return WebApiResponse.error("商品 ["+ dto.getGoodsName() + "] 已经删除");
            }

            /**
             * 商品上架状态：0：未上架，1：已上架，2：延迟上架
             */
            if(GoodsContant.GooodsStatusType.GOOODS_SALE_Y.getValue() != dto.getStatus().intValue()){
                return WebApiResponse.error("商品 ["+ dto.getGoodsName() + "] 未上架");
            }

            dto.setQuantity(mapGoods.get(dto.getGoodsUuid()).getQuantity());

            if (dto.getQuantity() <= 0){
                return WebApiResponse.error("商品 ["+ dto.getGoodsName() + "] 数量必须大于0");
            }

            price += dto.getQuantity() * dto.getStandardPrice();

            quantity += dto.getQuantity();
        }

        //创建主订单
        String sn = getSn();

        if(StringUtils.isEmpty(sn)){

            return WebApiResponse.error("创建订单号失败");
        }


        //订单号创建成功,发送消息通知服务端
        OrderCreateMqDTO dto = new OrderCreateMqDTO();
        dto.setOrderSn(sn);
        dto.setMemberUuid(memberUuid);
        dto.setOrderDTO(orderDTO);
        dto.setQuantity(quantity);
        dto.setPrice(price);
        dto.setGoodsOrderDTOList(goodsOrderDTOList);
        sendOrderSn(dto);

        return WebApiResponse.success(dto.getOrderSn());
    }

    /**
     * 订单号创建成功
     * @param dto
     */
    private void sendOrderSn(OrderCreateMqDTO dto){
        rabbitTemplate.convertAndSend(RabbitmqConstants.MallCreateOrder.EXCHANGE.getValue(), RabbitmqConstants.MallCreateOrder.ROUTING.getValue(), dto);
    }

    private String queryMemberName(String memberUuid)
    {
        Member member = memberDubboService.selectByUuid(memberUuid);
        return member != null ? member.getUserName() : null;
    }

    /**
     * 删除订单
     * @param orderSn
     * @param token
     * @return
     * @throws Exception
     */
    @Override
    public WebApiResponse<?> delOrder(String orderSn, String token) throws Exception {

        String memberUuid = getMember(token);

        OrderItem orderItem = new OrderItem();
        orderItem.setSn(orderSn);
        orderItem.setModifyDate(new Date());
        orderItem.setModifier(memberUuid);
        orderItem.setDelFlag(true);


        OrderItem var = new OrderItem();
        var.setSn(orderSn);
        var.setDelFlag(false);
        OrderItem item = orderItemDubboService.selectOne(var);

        int result = orderItemDubboService.updateOrderItemBySn(orderItem);

        if (result > 0){

            //发送消息更新缓存
            String cacheKey= redisManager.generateCacheKey(SingleOrderDTO.class, orderItem.getSn());
            UpdateCacheQueueDTO queue=new UpdateCacheQueueDTO();
            queue.setCacheKey(cacheKey);
            rabbitTemplate.convertAndSend(CacheMqNameConstans.EXCHANGE_NAME,CacheMqNameConstans.ROUTING_KEY_NAME, queue);

            //删除主订单
            Orders orders = new Orders();
            orders.setUuid(item.getOrderUuid());
            orders.setDelFlag(true);
            orders.setModifier(memberUuid);
            orders.setModifyDate(new Date());
            orderDubboService.updateByUuid(orders);

            createOrderLog(item.getUuid(), memberUuid, "删除订单", orderSn);

        }

        return result > 0 ? WebApiResponse.success():WebApiResponse.error("删除订单失败");
    }

    /**
     * 订单评论
     * @param commentDTO
     * @return
     * @throws Exception
     */
    @Override
    public WebApiResponse<?> comment(OrderCommentDTO commentDTO) throws Exception {

        OrderItem var = new OrderItem();
        var.setSn(commentDTO.getOrderSn());
        var.setDelFlag(false);
        OrderItem item = orderItemDubboService.selectOne(var);

        if (item == null){
            return WebApiResponse.error("订单号不存在");
        }


        String memberUuid = getMember(commentDTO.getToken());

        BuyersShow buyersShow = new BuyersShow();
        buyersShow.setGoodsUuid(item.getProductUuid());
        buyersShow.setOrderUuid(item.getUuid());
        buyersShow.setAnonymousFlag(commentDTO.getAnonymousFlag());
        buyersShow.setContent(commentDTO.getContent());
        buyersShow.setOrderSn(commentDTO.getOrderSn());
        buyersShow.setCreateDate(new Date());
        buyersShow.setCreator(memberUuid);
        buyersShow.setMemberUuid(memberUuid);
        buyersShow.setMemberName(queryMemberName(memberUuid));
        String memberName = memberDubboService.selectByUuid(memberUuid)==null?"":memberDubboService.selectByUuid(memberUuid).getUserName();
        buyersShow.setMemberName(memberName);
        buyersShow.setDelFlag(false);
        buyersShow.setStatus(true);
        String uuid = buyersShowDubboService.insertReturnUuid(buyersShow);



        if (StringUtils.isEmpty(uuid)){
            return WebApiResponse.error("评论失败");
        }

        buyersShow.setUuid(uuid);

        if (commentDTO.getListFiles() != null && commentDTO.getListFiles().size() > 0){

            for (String img : commentDTO.getListFiles()){

                BuyersShowImg buyersShowImg = new BuyersShowImg();
                buyersShowImg.setBuyersUuid(buyersShow.getUuid());
                buyersShowImg.setCreateDate(new Date());
                buyersShowImg.setCreator(memberUuid);
                buyersShowImg.setImgUrl(img);
                buyersShowImg.setDelFlag(false);

                buyersShowImgDubboService.insert(buyersShowImg);
            }

        }

        /**
         * 过滤敏感数据
         */
        OrderCommentImgDTO dto = new OrderCommentImgDTO();
        dto.setBuyersUuid(buyersShow.getUuid());
        dto.setMemberUuid(memberUuid);
        rabbitTemplate.convertAndSend(RabbitmqConstants.BuyersShowEnum.EXCHANGE.getValue(), RabbitmqConstants.BuyersShowEnum.ROUTING.getValue(), dto);

        return WebApiResponse.success();
    }

    /**
     * 查看订单
     * @param orderSn
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public WebApiResponse<?> showSingleOrderInfo(String orderSn) throws Exception {

        String cacheKey= redisManager.generateCacheKey(SingleOrderDTO.class, orderSn);

        SingleOrderDTO dto = null;

        String cacheData=redisManager.get(cacheKey);

        if(!StringUtils.isEmpty(cacheData)) {

            dto = JSON.parseObject(cacheData, SingleOrderDTO.class);

        }else{
            //获取失效时间，如果已经失效，再查询数据库，即使为空直，如果没失效，也不会直接访问数据库
            long expire = redisManager.getExpire(cacheKey);
            if (expire <= 0) {

                dto = orderItemDubboService.selectOrderItemInfoBySn(orderSn);

                if (dto == null){
                    return WebApiResponse.error("订单号不存在");
                }

                List<String> listFigureUuid = Arrays.asList(dto.getFigureUuid());
                List<String> listGoodsUuid = Arrays.asList(dto.getGoodsUuid());

                //多线程查询其他库
                ExecutorService executor = null;

                try {

                    executor = Executors.newFixedThreadPool(2);

                    Future<List<GoodsDTO.CoverInfo>> futureCover = executor.submit(() -> goodsDubboService.getGoodsCoverListByIds(listGoodsUuid));

                    Future<List<Figure>> futureFigure = executor.submit(() -> figureDubboService.selectInUuid(listFigureUuid));

                    Map<String, GoodsDTO.CoverInfo> mapCover = futureCover.get()
                            .stream()
                            .collect(Collectors.toMap(GoodsDTO.CoverInfo::getUuid, Function.identity()));

                    Map<String, String> mapFigure = futureFigure.get()
                            .stream()
                            .collect(Collectors.toMap(Figure::getUuid, figure -> figure.getFigureName()));

                    dto.setFigureName(mapFigure == null ? "" : mapFigure.get(dto.getFigureUuid()));

                    if (mapCover != null){
                        GoodsDTO.CoverInfo coverInfo = mapCover.get(dto.getGoodsUuid());

                        dto.setCover(coverInfo == null ? "" : coverInfo.getCover());

                        dto.setMaterielInfo(coverInfo == null ? "" : coverInfo.getMaterielInfo());
                    }
                }finally {
                    if (executor != null){
                        stop(executor);
                    }
                }

                if (null == dto) {
                    //如果数据库为空，设置一个null值的失效时间，防止缓存击穿
                    redisManager.setNull(cacheKey);
                } else {
                    //加入缓存
                    redisManager.set(cacheKey, JSON.toJSONString(dto), redisManager.getRandomExpire(), TimeUnit.DAYS);
                }

            }
        }

        return WebApiResponse.success(dto);
    }


    /**
     * 修改订单状态
     * @param statusReqDTO
     * @return
     * @throws Exception
     */
    @Override
    public WebApiResponse<?> updateOrderStatus(OrderStatusReqDTO statusReqDTO) throws Exception {

        //主订单
        Orders var = new Orders();
        var.setDelFlag(false);
        var.setUuid(statusReqDTO.getOrderUuId());
        var.setSn(statusReqDTO.getOrderSn());
        List<Orders> ordersList = orderDubboService.select(var);

        //子订单
        OrderItem var1 = new OrderItem();
        var1.setDelFlag(false);
        var1.setUuid(statusReqDTO.getOrderUuId());
        var1.setSn(statusReqDTO.getOrderSn());

        List<OrderItem> orderItemList = orderItemDubboService.select(var1);

        if (ordersList.isEmpty() && orderItemList.isEmpty()){
            return WebApiResponse.error("订单不存在");
        }

        if (statusReqDTO.getStatus() == null){

            return WebApiResponse.error("订单状态不正确");
        }

        String creator = getMember(statusReqDTO.getToken());

        int result = 0;

        //修改主订单状态
        if (ordersList.size() > 0){

            Orders orders = ordersList.get(0);

            if (orders.getStatus() == null || statusReqDTO.getStatus() == null
                    || orders.getStatus().compareTo(statusReqDTO.getStatus()) == 0){

                return WebApiResponse.error("订单状态不正确");
            }

            //已付款的订单不能取消
            if (orders.getStatus().intValue() > 0 && statusReqDTO.getStatus().intValue() == Orders.OrderStatus.canceled.getValue()){

                return WebApiResponse.error("订单状态 "+Orders.OrderStatus.keys(orders.getStatus()) + " 不能取消");
            }

            //修改
            if(statusReqDTO.getStatus().intValue() == Orders.OrderStatus.producing.getValue()){

                if (orders.getStatus().intValue() != 0 || statusReqDTO.getPaymentMethodType() == null){
                    return WebApiResponse.error("修改支付方式失败");
                }

            }

            Orders updateOrder = new Orders();
            updateOrder.setStatus(statusReqDTO.getStatus());
            updateOrder.setModifyDate(new Date());
            updateOrder.setSn(statusReqDTO.getOrderSn());
            updateOrder.setModifier(creator);
            updateOrder.setPaymentMethodType(statusReqDTO.getPaymentMethodType());
            result = orderDubboService.updateOrderBySn(updateOrder);
        }

        //修改子订单
        if (orderItemList.size() > 0){
            result = updateOrderItemStatus(statusReqDTO, orderItemList, creator, result);
        }

        //已支付成功 通知CIPP
        if(result > 0 && statusReqDTO.getStatus().intValue() == Orders.OrderStatus.producing.getValue()){


            /*
            message.setOrderSn(item.getSn());
            message.setMemberUuid(creator);
            rabbitTemplate.convertAndSend(RabbitmqConstants.OrderIncomeEnum.EXCHANGE.getValue(),
                    RabbitmqConstants.OrderIncomeEnum.ROUTING.getValue());
            */
        }


        return result > 0 ? WebApiResponse.success() : WebApiResponse.error("没有修改订单状态");
    }

    /**
     * 修改子订单
     * @param statusReqDTO
     * @param orderItemList
     * @param creator
     * @param result
     * @return
     */
    private int updateOrderItemStatus(OrderStatusReqDTO statusReqDTO, List<OrderItem> orderItemList, String creator, int result) {

        for (OrderItem item : orderItemList) {

            OrderItem orderItem = new OrderItem();
            orderItem.setSn(item.getSn());
            orderItem.setStatus(statusReqDTO.getStatus());
            orderItem.setModifyDate(new Date());
            orderItem.setModifier(creator);

            result = orderItemDubboService.updateOrderItemBySn(orderItem);

            //日志
            if (result > 0){

                //发送消息更新缓存
                String cacheKey = redisManager.generateCacheKey(SingleOrderDTO.class, orderItem.getSn());
                UpdateCacheQueueDTO queue = new UpdateCacheQueueDTO();
                queue.setCacheKey(cacheKey);
                rabbitTemplate.convertAndSend(CacheMqNameConstans.EXCHANGE_NAME,CacheMqNameConstans.ROUTING_KEY_NAME, queue);

                //订单确认收货 增加会员收益
                if (statusReqDTO.getStatus() == Orders.OrderStatus.comment.getValue()){

                    OrderIncomeMqDTO message = new OrderIncomeMqDTO();
                    message.setOrderSn(item.getSn());
                    message.setMemberUuid(creator);
                    rabbitTemplate.convertAndSend(RabbitmqConstants.OrderIncomeEnum.EXCHANGE.getValue(),
                            RabbitmqConstants.OrderIncomeEnum.ROUTING.getValue());
                }

                StringBuffer contentsb = new StringBuffer("商品 ");
                contentsb.append(item.getName());
                contentsb.append("订单状态修改为:");
                contentsb.append(Orders.OrderStatus.keys(statusReqDTO.getStatus()));

                if (!StringUtils.isEmpty(statusReqDTO.getContent())) {
                    contentsb.append(" 原因 :");
                    contentsb.append(statusReqDTO.getContent());
                }

                createOrderLog(item.getUuid(), creator, contentsb.toString(), orderItem.getSn());
            }
        }
        return result;
    }

    /**
     * 查询换货原因
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public WebApiResponse<?> queryExchangeReason() throws Exception {
        return WebApiResponse.success(baseDataService.queryExchangeReason());
    }

    /**
     * 查看售后进度
     * @param orderSn
     * @return
     * @throws Exception
     */
    @Override
    public WebApiResponse<?> queryServiceDetail(String orderSn) throws Exception {

        List<AfterSaleSchedule> scheduleList = afterSaleScheduleDubboService.selectScheduleByOrderSn(orderSn);

        AfterSaleService var1 = new AfterSaleService();
        var1.setOrderSn(orderSn);
        var1.setDelFlag(false);

        AfterSaleService afterSaleService = afterSaleServiceDubboService.selectOne(var1);

        if (afterSaleService == null || scheduleList.size() <= 0){
            return WebApiResponse.error("没有售后记录");
        }

        SaleServiceDetailDTO dto = new SaleServiceDetailDTO();

        List result = new ArrayList();

        SaleServiceDetailDTO.SaleServiceDetail detail = new SaleServiceDetailDTO.SaleServiceDetail();
        detail.setCreateDate(afterSaleService.getCreateDate());
        detail.setExchangeReason(afterSaleService.getExchangeReason());
        detail.setOrderSn(orderSn);

        dto.setListSchedule(result);
        dto.setDetail(detail);

        for (AfterSaleSchedule s : scheduleList){
            switch (s.getStatus()){

                //提交售后成功，等待审核
                case 0:
                    SaleServiceDetailDTO.ApplyService applyService = new SaleServiceDetailDTO.ApplyService();
                    applyService.setCreateDate(s.getCreateDate());
                    applyService.setStatus(s.getStatus());
                    applyService.setMemo(s.getMemo());

                    result.add(applyService);
                    break;

                case 1:
                    SaleServiceDetailDTO.AuditingInfo auditingInfo = new SaleServiceDetailDTO.AuditingInfo();
                    auditingInfo.setAddress("收货地址");
                    auditingInfo.setBackAddress("退货地址");
                    auditingInfo.setConsignee("收货人");
                    auditingInfo.setFlag(afterSaleService.getAuditingFlag());
                    auditingInfo.setPhone("收货人手机号");
                    auditingInfo.setRejectReason(afterSaleService.getRejectReason());
                    auditingInfo.setCreateDate(s.getCreateDate());
                    auditingInfo.setStatus(s.getStatus());
                    auditingInfo.setMemo(s.getMemo());

                    result.add(auditingInfo);
                    break;

                case 2:
                    SaleServiceDetailDTO.ConfirmInfo confirmInfo = new SaleServiceDetailDTO.ConfirmInfo();
                    confirmInfo.setCreateDate(s.getCreateDate());
                    confirmInfo.setStatus(s.getStatus());
                    confirmInfo.setMemo(s.getMemo());

                    result.add(confirmInfo);
                    break;

                //生产中
                case 3:
                    //查询生产进度
                    List<SaleServiceDetailDTO.ProducingInfo> producingInfoList = queryProducingInfo(orderSn);

                    result.add(producingInfoList);
                    break;

                //已包装发货，等待收货
                case 4:

                    SaleServiceDetailDTO.GoodsReceipt goodsReceipt = new SaleServiceDetailDTO.GoodsReceipt();
                    goodsReceipt.setCreateDate(s.getCreateDate());
                    goodsReceipt.setStatus(s.getStatus());
                    goodsReceipt.setMemo(s.getMemo());
                    goodsReceipt.setShippingNumber(afterSaleService.getShippingNumber());

                    result.add(goodsReceipt);

                    //查询物流进度
                    List<SaleServiceDetailDTO.ShippingInfo> shippingInfoList = queryShippingInfo(afterSaleService.getShippingNumber());
                    result.add(shippingInfoList);
                    break;

                case 5:
                    SaleServiceDetailDTO.Completed completed = new SaleServiceDetailDTO.Completed();
                    completed.setCreateDate(s.getCreateDate());
                    completed.setStatus(s.getStatus());
                    completed.setMemo(s.getMemo());
                    result.add(completed);
                    break;

                default: break;
            }

        }

        return WebApiResponse.success(dto);
    }

    /**
     * 查询售后列表
     * @param token
     * @return
     * @throws Exception
     */
    @Override
    public WebApiResponse<?> querySaleServiceList(Integer pageNo, Integer pageSize, String token) throws Exception {

        String memberUuid = getMember(token);

        if (StringUtils.isEmpty(memberUuid)){
            return WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
        }

        int offset = (pageNo <= 0 ? pageNo : pageNo - 1) * pageSize;


        List<AfterSaleServiceResp> respList = afterSaleServiceDubboService.selectSaleServiceList(offset, pageSize, memberUuid);

        if (respList.isEmpty()){
            return WebApiResponse.success(respList);
        }

        List<String> listFigureUuid = new ArrayList<>();
        List<String> listGoodsUuid = new ArrayList<>();
        respList.forEach(dto -> {
            listFigureUuid.add(dto.getFigureUuid());
            listGoodsUuid.add(dto.getGoodsUuid());
        });

        //多线程查询其他库
        ExecutorService executor = Executors.newFixedThreadPool(2);
        try {

            Future<List<GoodsDTO.CoverInfo>> futureCover = executor.submit(() -> goodsDubboService.getGoodsCoverListByIds(listGoodsUuid));

            Future<List<Figure>> futureFigure = executor.submit(() -> figureDubboService.selectInUuid(listFigureUuid));

            Map<String, String> mapCover = futureCover.get()
                    .stream()
                    .collect(Collectors.toMap(GoodsDTO.CoverInfo::getUuid, c -> c.getCover()));

            Map<String, String> mapFigure = futureFigure.get()
                    .stream()
                    .collect(Collectors.toMap(Figure::getUuid, figure -> figure.getFigureName()));

            respList.forEach(dto ->{
                dto.setFigureName(mapFigure == null ? "" : mapFigure.get(dto.getFigureUuid()));
                dto.setCover(mapCover == null ? "" : mapCover.get(dto.getGoodsUuid()));
            });

        }catch (Exception e){

            throw e;

        }finally {
            executor.shutdown();
        }

        return WebApiResponse.success(respList);
    }

    /**
     * 查询退货生产进度
     * @param orderSn
     */
    private List<SaleServiceDetailDTO.ProducingInfo> queryProducingInfo(String orderSn){
        List<SaleServiceDetailDTO.ProducingInfo> list = new ArrayList<>();

        SaleServiceDetailDTO.ProducingInfo producingInfo = new SaleServiceDetailDTO.ProducingInfo();
        producingInfo.setCreateDate(new Date());
        producingInfo.setMemo("到达深圳站");

        SaleServiceDetailDTO.ProducingInfo producingInfo1 = new SaleServiceDetailDTO.ProducingInfo();
        producingInfo1.setMemo("到达南山");
        producingInfo1.setCreateDate(new Date());

        list.add(producingInfo);
        list.add(producingInfo1);

        return list;
    }

    /**
     * 查询退货 物流进度
     * @param shippingNumber
     */
    private List<SaleServiceDetailDTO.ShippingInfo> queryShippingInfo(String shippingNumber){
        SaleServiceDetailDTO.ShippingInfo shippingInfo = new SaleServiceDetailDTO.ShippingInfo();
        shippingInfo.setMemo("到达深圳站");
        shippingInfo.setCreateDate(new Date());

        SaleServiceDetailDTO.ShippingInfo shippingInfo1 = new SaleServiceDetailDTO.ShippingInfo();
        shippingInfo1.setMemo("到达南山");
        shippingInfo1.setCreateDate(new Date());

        List<SaleServiceDetailDTO.ShippingInfo> list = new ArrayList<>();

        list.add(shippingInfo);
        list.add(shippingInfo1);

        return list;
    }

    /**
     * 根据城市选择合适的物流
     * @param areaUuid
     * @return
     * @throws Exception
     */
    @Override
    public WebApiResponse<?> queryShipping(String areaUuid) throws Exception {
        return WebApiResponse.success(shippingMethodDubboService.selectShippingInfoByAreaUuid(Long.valueOf(areaUuid)));
    }

    /**
     * 申请售后服务
     * @param dto
     * @return
     * @throws Exception
     */
    @Override
    public WebApiResponse<?> afterSaleService(AfterSaleServiceDTO dto) throws Exception {

        AfterSaleService var1 = new AfterSaleService();
        var1.setOrderSn(dto.getOrderSn());
        var1.setDelFlag(false);

        if(afterSaleServiceDubboService.selectCount(var1) > 0){
            return WebApiResponse.error("售后服务不能重复申请");
        }


        OrderItem var = new OrderItem();
        var.setSn(dto.getOrderSn());
        var.setDelFlag(false);
        OrderItem item = orderItemDubboService.selectOne(var);


        String memberUuid = getMember(dto.getToken());

        AfterSaleService saleService = new AfterSaleService();
        saleService.setApplyServiceType(dto.getApplyServiceType());
        saleService.setApplyServiceName(AfterSaleService.applyType.getNameValueByKey(dto.getApplyServiceType()));
        saleService.setExchangeReason(dto.getExchangeReason());
        saleService.setMemo(dto.getMemo());
        saleService.setImg(JSON.toJSON(dto.getListImg()).toString());
        saleService.setOrderSn(dto.getOrderSn());
        saleService.setGoodsUuid(item.getProductUuid());
        saleService.setMemberUuid(memberUuid);
        saleService.setMemberName(queryMemberName(memberUuid));
        saleService.setCreateDate(new Date());
        saleService.setCreator(memberUuid);
        saleService.setVersion(0);
        saleService.setDelFlag(false);
        saleService.setStatus(0);

        int result = afterSaleServiceDubboService.insert(saleService);

        if (result > 0 ){

            //售后进度信息
            AfterSaleSchedule schedule = new AfterSaleSchedule();
            schedule.setGoodsUuid(saleService.getGoodsUuid());
            schedule.setSaleServiceUuid(saleService.getUuid());
            schedule.setOrderSn(saleService.getOrderSn());
            schedule.setStatus(saleService.getStatus());
            schedule.setDelFlag(false);
            schedule.setVersion(0);
            schedule.setCreateDate(new Date());
            schedule.setCreator(memberUuid);
            schedule.setMemo("提交售后成功,等待审核");

            afterSaleScheduleDubboService.insert(schedule);

            createOrderLog(item.getUuid(), memberUuid, "申请售后服务",dto.getOrderSn());
        }

        return result > 0 ? WebApiResponse.success():WebApiResponse.error("申请售后服务失败");
    }

    /**
     * 撤销售后
     * @param dto
     * @return
     * @throws Exception
     */
    @Override
    public WebApiResponse<?> cancelSaleService(CancelServiceDTO dto) throws Exception {

        OrderItem var = new OrderItem();
        var.setSn(dto.getOrderSn());
        var.setDelFlag(false);
        OrderItem item = orderItemDubboService.selectOne(var);

        //删除申请
        AfterSaleService saleService = new AfterSaleService();

        saleService.setOrderSn(dto.getOrderSn());

        String memberUuid = getMember(dto.getToken());

        saleService.setModifier(memberUuid);
        saleService.setModifyDate(new Date());
        saleService.setDelFlag(true);

        int result = afterSaleServiceDubboService.updateAfterSaleServiceByOrderSn(saleService);

        //删除进度
        AfterSaleSchedule schedule = new AfterSaleSchedule();
        schedule.setOrderSn(dto.getOrderSn());
        schedule.setModifier(memberUuid);
        schedule.setModifyDate(new Date());
        schedule.setDelFlag(true);

        afterSaleScheduleDubboService.updateSelectiveByOrderSn(schedule);

        if (result > 0 ){

            //软删除售后记录

            createOrderLog(item.getUuid(), memberUuid, "撤销售后", dto.getOrderSn());
        }


        return result > 0 ? WebApiResponse.success():WebApiResponse.error("撤销售后失败");
    }

    /**
     * 关闭订单
     * @param dto
     * @return
     * @throws Exception
     */
    @Override
    public WebApiResponse<?> closeOrder(CancelServiceDTO dto) throws Exception {

        //删除售后
        cancelSaleService(dto);

        //删除订单
        delOrder(dto.getOrderSn(), dto.getToken());

        return WebApiResponse.success();
    }


    /**
     * 生成日志
     * @param creator
     * @param orderUuid
     */
    private void createOrderLog(String orderUuid, String creator, String content, String orderSn){
        //生成日志
        OrderLog orderLog = new OrderLog();
        orderLog.setCreateDate(new Date());
        orderLog.setCreator(creator);
        orderLog.setModifyDate(new Date());
        orderLog.setVersion(0);
        orderLog.setContent(content);
        orderLog.setType(0);
        orderLog.setOrderUuid(orderUuid);
        orderLog.setDelFlag(false);
        orderLog.setOrderSn(orderSn);

        orderLogDubboService.insert(orderLog);
    }


    /**
     * 关闭线程池
     * @param executor
     */
    static void stop(ExecutorService executor){
        try {

            executor.shutdown();
            executor.awaitTermination(3, TimeUnit.SECONDS);

        }catch (InterruptedException e){

            LOGGER.error("线程中断异常:" + e.getMessage());
            Thread.currentThread().interrupt();

        }finally {

            if (!executor.isTerminated()){
                LOGGER.error("杀死未完成的任务");
            }

            executor.shutdownNow();
        }

    }

}
