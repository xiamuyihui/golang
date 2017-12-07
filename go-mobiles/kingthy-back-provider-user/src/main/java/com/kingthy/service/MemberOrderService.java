/**
 * 系统项目名称
 * com.kingthy.service
 * MemberOrderService.java
 * 
 * 2017年4月24日-上午9:43:39
 *  2017金昔科技有限公司-版权所有
 *
 */
package com.kingthy.service;

import com.github.pagehelper.PageInfo;
import com.kingthy.dto.MemberOrderDTO;
import com.kingthy.request.MemberOrderReq;

import java.util.List;

/**
 *
 * MemberOrderService
 * 
 * @author 李克杰 2017年4月24日 上午9:43:39
 * 
 * @version 1.0.0
 *
 */
public interface MemberOrderService
{
    /**
     * 获取会员订单列表
     * @param memberUuid
     * @param orderStatus
     * @return
     */
    List<MemberOrderDTO> getMemberOrderList(String memberUuid, Integer orderStatus);
    
    /**
     * 分页获取会员订单列表
     * @param req
     * @return
     */
    PageInfo<MemberOrderDTO> pageGetOrderList(MemberOrderReq req);
}
