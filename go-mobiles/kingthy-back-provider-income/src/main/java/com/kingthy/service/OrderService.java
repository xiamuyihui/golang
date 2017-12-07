package com.kingthy.service;

import java.math.BigDecimal;

import com.kingthy.dto.*;
import com.kingthy.entity.Cart;
import com.kingthy.entity.Orders;
import com.kingthy.entity.Payment;
import com.kingthy.entity.PaymentMethod;
import com.kingthy.entity.Receiver;
import com.kingthy.entity.ShippingMethod;
import com.kingthy.response.WebApiResponse;
/**
 * @author:xumin
 * @Description:
 * @Date:17:26 2017/11/1
 */
public interface OrderService
{
    /**
     * 订单创建
     * 
     * @param type 类型
     * @param cart 购物车
     * @param receiver 收货地址
     * @param paymentMethod 支付方式
     * @param shippingMethod 配送方式
     * @param invoice 发票
     * @param memo 附言
     * @return 订单
     * @deprecated
     */
    @Deprecated
    Orders createOrder(Orders.OrderType type, Cart cart, Receiver receiver, PaymentMethod paymentMethod,
        ShippingMethod shippingMethod, Invoice invoice, String memo);

    /**
     * 创建订单
     * @param orderDTO
     * @return
     * @throws Exception
     */
    WebApiResponse<?> createOrder(OrderDTO orderDTO) throws Exception;

    /**
     * 确认支付页面接口
     * @param confirmPayDTO
     * @return
     * @throws Exception
     * @deprecated
     */
    @Deprecated
    WebApiResponse<?> showConfirmPay(OrderConfirmPayDTO confirmPayDTO) throws Exception;

    /**
     * 删除订单
     * @param orderSn
     * @param token
     * @return
     * @throws Exception
     */
    WebApiResponse<?> delOrder(String orderSn,String token) throws Exception;

    /**
     * 订单评论
     * @param commentDTO
     * @return
     * @throws Exception
     */
    WebApiResponse<?> comment(OrderCommentDTO commentDTO) throws Exception;

    /**
     * 查看订单
     * @param orderSn
     * @return
     * @throws Exception
     */
    WebApiResponse<?> showSingleOrderInfo(String orderSn) throws Exception;

    /**
     * 修改订单状态
     * @param statusReqDTO
     * @return
     * @throws Exception
     */
    WebApiResponse<?> updateOrderStatus(OrderStatusReqDTO statusReqDTO) throws Exception;

    /**
     * 查询换货原因
     * @return
     * @throws Exception
     */
    WebApiResponse<?> queryExchangeReason() throws Exception;

    /**
     * 查看售后进度
     * @param orderSn
     * @return
     * @throws Exception
     */
    WebApiResponse<?> queryServiceDetail(String orderSn) throws Exception;

    /**
     * 查询售后列表
     * @param token
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    WebApiResponse<?> querySaleServiceList(Integer pageNo, Integer pageSize, String token) throws Exception;

    /**
     * 根据城市选择合适的物流
     * @param areaUuid
     * @return
     * @throws Exception
     */
    WebApiResponse<?> queryShipping(String areaUuid) throws Exception;

    /**
     * 申请售后服务
     * @param dto
     * @return
     * @throws Exception
     */
    WebApiResponse<?> afterSaleService(AfterSaleServiceDTO dto) throws Exception;


    /**
     * 回填物流单号
     * @param dto
     * @return
     * @throws Exception
     */
    WebApiResponse<?> updateShippingNumber(SaleServiceShippingDTO dto) throws Exception;

    /**
     * 撤销售后
     * @param dto
     * @return
     * @throws Exception
     */
    WebApiResponse<?> cancelSaleService(CancelServiceDTO dto) throws Exception;

    /**
     * 关闭订单
     * @param dto
     * @return
     * @throws Exception
     */
    WebApiResponse<?> closeOrder(CancelServiceDTO dto) throws Exception;

    /**
     * 订单锁定
     * 
     * @param orders 订单
     * @param memberUuid 操作员
     * @return
     */
    boolean lockOrder(Orders orders, String memberUuid);
    
    /**
     * 订单更新
     * 
     * @param orders 订单
     * @param memberUuid 操作员
     */
    void update(Orders orders, String memberUuid);
    
    /**
     * 订单取消
     * 
     * @param orders 订单
     * @param memberUuid 订单
     */
    void cancel(Orders orders, String memberUuid);
    
    /**
     * 订单审核
     * 
     * @param orders 订单
     * @param passed 是否审核通过
     */
    void review(Orders orders, boolean passed);
    
    /**
     * 订单收款
     * 
     * @param orders 订单
     * @param payment 收款单
     */
    void payment(Orders orders, Payment payment);
    
    /**
     * 订单发货
     * 
     * @param order 订单
     * @param shipping 发货单
     * @param operator 操作员
     */
    // void shipping(TbOrder order, TbShipping shipping);
    
    /**
     * 订单退货
     * 
     * @param order 订单
     * @param returns 退货单
     * @param operator 操作员
     */
    // void returns(TbOrder order, Returns returns);
    
    /**
     * 订单收货
     * 
     * @param orders 订单
     */
    void receive(Orders orders);
    
    /**
     * 订单完成
     * 
     * @param orders 订单
     * @param memberUuid 操作员
     */
    void complete(Orders orders, String memberUuid);
    
    /**
     * 订单失败
     * 
     * @param orders 订单
     */
    void fail(Orders orders);
    
    /**
     * 根据订单号查询订单
     * 
     * @param sn 订单编号
     * @return
     */
    Orders findBySn(String sn);


    /**
     * 释放内存
     */
    void releaseExpiredAllocatedStock();
    
    /**
     * 计算税金
     * 
     * @param price 商品价格
     * @param promotionDiscount 促销折扣
     * @param couponDiscount 优惠券折扣
     * @param offsetAmount 调整金额
     * @return 税金
     */
    BigDecimal calculateTax(BigDecimal price, BigDecimal promotionDiscount, BigDecimal couponDiscount,
        BigDecimal offsetAmount);
    
    /**
     * 计算税金
     * 
     * @param order 订单
     * @return 税金
     */
    BigDecimal calculateTax(Orders order);
    
    /**
     * 计算订单金额
     * 
     * @param calculateAmountDTO 调整金额
     * @return 订单金额
     */
    BigDecimal calculateAmount(CalculateAmountDTO calculateAmountDTO);
    
    /**
     * 计算订单金额
     * 
     * @param orders 订单
     * @return 订单金额
     */
    BigDecimal calculateAmount(Orders orders);
}
