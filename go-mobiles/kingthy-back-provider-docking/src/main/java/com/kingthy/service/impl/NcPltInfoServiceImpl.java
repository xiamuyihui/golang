package com.kingthy.service.impl;

import com.google.gson.Gson;
import com.kingthy.constant.RabbitmqConstants;
import com.kingthy.dto.NcpltInfoDTO;
import com.kingthy.request.CuttingMachineReq;
import com.kingthy.request.PaperModelReq;
import com.kingthy.response.NcpltInfoResp;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.CuttingMachineService;
import com.kingthy.service.NcPltInfoService;
import com.kingthy.service.PaperModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 13:48 on 2017/10/11.
 * @Modified by:
 */

@Service
public class NcPltInfoServiceImpl implements NcPltInfoService
{

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private PaperModelService paperModelService;

    @Autowired
    private CuttingMachineService cuttingMachineService;

    /**
     * 生成排料后的纸样文件和裁床文件
     * @param dto
     * @return
     * @throws Exception
     */
    @Override
    public WebApiResponse<?> createNcpltInfo(NcpltInfoDTO dto)throws Exception{

        //判断参数不能为空
        for (Field f : dto.getClass().getDeclaredFields()){
            f.setAccessible(true);
            if (StringUtils.isEmpty(f.get(dto))){
                return WebApiResponse.error(WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
            }
        }

        //已经生成过了
        String redisKey = getNcPltRedisKey(dto);
        if (stringRedisTemplate.hasKey(redisKey)){
            return WebApiResponse.success();
        }

        //发送消息 或者 通过 FeignClient 调用几何接口 生成文件
        PaperModelReq req = new PaperModelReq();
        req.setExchange(RabbitmqConstants.NCPLT_FILE_EXCHANGE);
        req.setRoutingKey(RabbitmqConstants.NCPLT_FILE_ROUTING);
        req.setUuid(dto.getOrderItemSn());


        NcpltInfoResp resp = new NcpltInfoResp();
        resp.setOrderItemSn(dto.getOrderItemSn());
        resp.setStyleCode(dto.getStyleCode());
        resp.setMaterialCode(dto.getMaterialCode());

        Gson gson = new Gson();

        stringRedisTemplate.opsForValue().set(redisKey, gson.toJson(resp).toString(),60 * 60 * 24, TimeUnit.SECONDS);

        return WebApiResponse.success();
    }

    @Override
    public WebApiResponse<?> ncpltInfo(NcpltInfoDTO dto) throws Exception {

        Gson gson = new Gson();
        NcpltInfoResp resp = gson.fromJson(stringRedisTemplate.opsForValue().get(getNcPltRedisKey(dto)), NcpltInfoResp.class);

        return WebApiResponse.success(resp);
    }

    private String getNcPltRedisKey(NcpltInfoDTO dto){
        StringBuffer key = new StringBuffer("NCPLT:");
        key.append(dto.getOrderItemSn());

        return key.toString();
    }
}
