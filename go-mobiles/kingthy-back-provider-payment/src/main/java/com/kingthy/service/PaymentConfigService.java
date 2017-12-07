package com.kingthy.service;

import com.kingthy.dto.PaymentConfigDTO;

import java.util.List;

/**
 * @author likejie
 * Created by likejie on 2017/6/23.
 */
public interface PaymentConfigService {

    /**获取所有支付配置
     * @return
     */
    List<PaymentConfigDTO> getPaymentConfigList();

    /**
     * 获取支付配置
     * @param paymentType
     * @return
     */
    PaymentConfigDTO getPaymentConfig(int paymentType);
}
