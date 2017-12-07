package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.kingthy.dubbo.basedata.service.CollocationDubboService;
import com.kingthy.entity.Collocation;
import com.kingthy.exception.BizException;
import com.kingthy.request.QueryCollocationReq;
import com.kingthy.service.CollocationService;
import org.springframework.stereotype.Service;
import java.util.Date;


/**
 * CollocationServiceImpl(描述其作用)
 * <p>
 * @author 赵生辉 2017年05月23日 14:20
 *
 * @version 1.0.0
 */
@Service("collocationService")
public class CollocationServiceImpl implements CollocationService
{


    @Reference(version = "1.0.0")
    private CollocationDubboService collocationDubboService;

    @Override
    public int createCollocation(Collocation collocation)
    {
        Date date = new Date();
        collocation.setCreateDate(date);
        collocation.setModifyDate(date);
        collocation.setDelFlag(false);
        collocation.setVersion(1);
        collocation.setCreator("creator");
        collocation.setModifier("modifier");
        int result = collocationDubboService.insert(collocation);
        if(result == 0){
            throw new BizException("创建潮搭失败");
        }
        return result;
    }

    @Override
    public PageInfo<Collocation> queryCollocation(QueryCollocationReq collocationReq)
    {

        Collocation cond=new Collocation();
        cond.setColourUuid(collocationReq.getColourUuid());
        cond.setOccasionUuid(collocationReq.getOccasionUuid());
        cond.setStyleUuid(collocationReq.getStyleUuid());
        cond.setTemperatureUuid(collocationReq.getTemperatureUuid());
        return collocationDubboService.queryPage(collocationReq.getPageNo(),collocationReq.getPageSize(),cond);
    }
}
