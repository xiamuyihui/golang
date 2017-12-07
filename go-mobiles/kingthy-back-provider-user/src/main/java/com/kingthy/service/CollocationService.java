package com.kingthy.service;

import com.github.pagehelper.PageInfo;
import com.kingthy.entity.Collocation;
import com.kingthy.request.QueryCollocationReq;

/**
 * CollocationService(描述其作用)
 * <p>
 * @author  赵生辉 2017年05月23日 14:18
 *
 * @version 1.0.0
 */
public interface CollocationService
{
    /**
     * 模拟创建潮搭
     * @param collocation
     * @return
     */
    int createCollocation(Collocation collocation);

    /**
     * 查询潮搭
     * @param queryCollocationReq
     * @return
     */
    PageInfo<Collocation> queryCollocation(QueryCollocationReq queryCollocationReq);
}
