package com.kingthy.service;

import com.kingthy.request.CalculatePriceReq;
import com.kingthy.response.WebApiResponse;
import org.springframework.stereotype.Service;


/**
 * 描述：GradedService
 * @author  赵生辉
 * @date 2017/10/10.
 */
@Service
public interface GradedService
{

    /**
     * GradedCommand
     * @param  req
     * @return
     */
    WebApiResponse<?> gradedCommand(CalculatePriceReq req);
}