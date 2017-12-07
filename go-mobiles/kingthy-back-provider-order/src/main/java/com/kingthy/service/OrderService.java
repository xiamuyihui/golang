package com.kingthy.service;

import com.kingthy.dto.*;
import com.kingthy.response.WebApiResponse;
/**
 * @author:xumin
 * @Description:
 * @Date:11:39 2017/11/2
 */
public interface OrderService
{


    /**
     * 创建订单
     * @param orderDTO
     * @return
     * @throws Exception
     */
    WebApiResponse<?> createOrder(OrderDTO orderDTO) throws Exception;

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

}
