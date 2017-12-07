package com.kingthy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.cache.RedisManager;
import com.kingthy.common.KryoSerializeUtils;
import com.kingthy.dto.PaymentConfigDTO;
import com.kingthy.dubbo.payment.service.PaymentConfigDubboService;
import com.kingthy.entity.PaymentConfig;
import com.kingthy.service.PaymentConfigService;

/**
 * @author likejie
 * Created by likejie on 2017/6/23.
 */
@Service
public class PaymentConfigServiceImpl implements PaymentConfigService
{
    
    @Reference(version = "1.0.0", timeout = 3000)
    private PaymentConfigDubboService paymentConfigDubboService;
    
    @Autowired
    private RedisManager redisManager;
    
    @Override
    public List<PaymentConfigDTO> getPaymentConfigList()
    {
        
        List<PaymentConfig> paymentConfigs = paymentConfigDubboService.selectAll();
        List<PaymentConfigDTO> paymentConfigDTOS = new ArrayList<>();
        PaymentConfigDTO paymentConfigDTO = new PaymentConfigDTO();
        paymentConfigs.forEach(paymentConfig -> {
            BeanUtils.copyProperties(paymentConfig, paymentConfigDTO);
            paymentConfigDTOS.add(paymentConfigDTO);
        });
        return paymentConfigDTOS;
    }
    
    @Override
    public PaymentConfigDTO getPaymentConfig(int paymentType)
    {
        String cacheKey = redisManager.generateCacheKey(PaymentConfigDTO.class, Integer.toString(paymentType));
        String cacheData = redisManager.get(cacheKey);
        PaymentConfigDTO paymentPlatformDTO =
            KryoSerializeUtils.deserializationObject(cacheData, PaymentConfigDTO.class);
        if (paymentPlatformDTO != null)
        {
            PaymentConfig cond = new PaymentConfig();
            cond.setType(paymentType);
            PaymentConfig config = paymentConfigDubboService.selectOne(cond);
            if (config != null)
            {
                paymentPlatformDTO.setAppId(config.getAppId());
                paymentPlatformDTO.setAppKey(config.getAppKey());
                paymentPlatformDTO.setAppSecret(config.getAppSecret());
                paymentPlatformDTO.setMchId(config.getMchId());
                paymentPlatformDTO.setName(config.getName());
                paymentPlatformDTO.setUuid(config.getUuid());
                paymentPlatformDTO.setType(config.getType());
                paymentPlatformDTO.setIsUsed(config.getIsUsed());
                redisManager.set(cacheKey, KryoSerializeUtils.serializationObject(paymentPlatformDTO));
            }
        }
        return paymentPlatformDTO;
    }
}
