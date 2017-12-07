package com.kingthy.service;


import com.kingthy.dto.RealNameVerifiedDTO;
import com.kingthy.dto.TransferDTO;
import com.kingthy.response.WebApiResponse;

/**
 *
 * @author likejie
 */
public interface UnionPayementService {

    /**
     *
     * 向银联发送代付申请
     * @param dto
     * @return WebApiResponse
     */
    WebApiResponse<?> sendTransferRequest(TransferDTO dto);
    /**
     *
     * 查询交易状态
     * @param orderId
     * @return WebApiResponse
     */
    WebApiResponse<?> queryTranStatus(String orderId);

    /**
     *
     * 实名认证
     * @param dto
     * @return WebApiResponse
     */
    WebApiResponse<?>  realNameVerified(RealNameVerifiedDTO dto);

    /**
     *
     * 银联代付回调
     */
    void unionTransferCallBack();

}
