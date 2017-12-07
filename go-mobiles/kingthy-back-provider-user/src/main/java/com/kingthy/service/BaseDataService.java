package com.kingthy.service;

import com.kingthy.entity.BaseData;

import java.util.List;

/**
 * BaseDataService(描述其作用)
 * <p>
 * @author 赵生辉 2017年05月11日 9:57
 *
 * @version 1.0.0
 */
public interface BaseDataService
{
    /**
     *
     * queryBaseData(chaxu)
     *
     * 赵生辉 2017/5/11 11:01
     *
     * @version 1.0.0
     * @param baseData
     * @return
     *
     */
    List<BaseData> queryBaseData(BaseData baseData);

    /**
     * 添加一个基础数据
     * @param baseData
     * @return
     */
    int createBaseData(BaseData baseData);
}
