package com.kingthy.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.kingthy.dto.MemberReceiverDTO;
import com.kingthy.entity.Receiver;
import com.kingthy.request.ReceiverReq;

/**
 *
 * 会员收获地址业务逻辑处理接口
 * 
 * @author likejie 2017-4-20
 * 
 * @version 1.0.0
 *
 */
public interface ReceiverService
{
    
    /**
     * 根据业务主键获取数据
     * 
     * @param uuid 会员收获地址业务主键
     * @return
     */
    Receiver getReceiverByUuid(String uuid);
    
    /**
     * 获取会员默认的收获地址
     * @param memberUuid 会员收获地址业务主键
     * @return
     */
    Receiver getDefaultReceiver(String memberUuid);
    
    /**
     * 获取会员收货地址
     * @param memberUuid 会员收获地址业务主键
     * @return
     */
    List<MemberReceiverDTO> getMemberReceiverList(String memberUuid);
    

    /**
     * 新增
     * @param receiver 会员收获地址业务主键
     * @return
     */
    int addReceiver(Receiver receiver);
    
    /**
     * 更新
     * @param receiver 会员收获地址对象
     * @return
     */
    int updateReceiver(Receiver receiver);
    
    /**
     * 删除
     * @param uuid 会员收获地址业务主键
     * @param memberUuid
     * @return
     */
    int deleteReceiver(String uuid,String memberUuid);
    
    /**
     * 设置默认收获地址
     * @param memberUuid
     * @param uuid
     * @return
     */
    int setDefaultReceiver(String memberUuid, String uuid);

    /**
     * 设置默认收获地址
     * @param req
     * @return
     */
    PageInfo<Receiver> pageGetReceiverList(ReceiverReq req);
    
}
