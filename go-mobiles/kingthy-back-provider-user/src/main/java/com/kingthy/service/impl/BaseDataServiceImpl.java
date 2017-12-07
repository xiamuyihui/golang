package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.dubbo.basedata.service.BaseDataDubboService;
import com.kingthy.entity.BaseData;
import com.kingthy.exception.BizException;
import com.kingthy.service.BaseDataService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BaseDataServiceImpl(描述其作用)
 * <p>
 * @author  赵生辉 2017年05月11日 9:58
 *
 * @version 1.0.0
 */
@Service("baseDataService")
public class BaseDataServiceImpl implements BaseDataService
{

    @Reference(version = "1.0.0")
    BaseDataDubboService baseDataDubboService;

    @Override
    public List<BaseData> queryBaseData(BaseData baseData)
    {
        List<BaseData> result =baseDataDubboService.queryBaseData(baseData);
        if(result == null)
        {
            throw new BizException("该类型数据不存在");
        }
        return result;
    }
    @Override
    public int createBaseData(BaseData baseData)
    {
        return baseDataDubboService.createBaseData(baseData);
    }
}
