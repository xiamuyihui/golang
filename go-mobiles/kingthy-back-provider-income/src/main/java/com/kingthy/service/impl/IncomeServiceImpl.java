package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.constant.RabbitmqConstants;
import com.kingthy.dto.IncomeBalanceDTO;
import com.kingthy.dto.IncomeDetailDTO;
import com.kingthy.dto.WithDrawsCashDTO;
import com.kingthy.dto.WithdrawsCashMqDTO;
import com.kingthy.dubbo.goods.service.GoodsDubboService;
import com.kingthy.dubbo.income.service.MemberIncomeDetailDubboService;
import com.kingthy.dubbo.income.service.MemberIncomeDubboService;
import com.kingthy.dubbo.income.service.MemberWithdrawsCashDubboService;
import com.kingthy.dubbo.income.service.WithdrawsCashLogDubboService;
import com.kingthy.dubbo.order.service.OrderItemDubboService;
import com.kingthy.dubbo.user.service.MemberBankCardDubboService;
import com.kingthy.entity.Goods;
import com.kingthy.entity.MemberBankCard;
import com.kingthy.entity.MemberWithdrawsCash;
import com.kingthy.request.BindingBankReq;
import com.kingthy.request.IncomeAddReq;
import com.kingthy.request.UpdateWithDrawStatusReq;
import com.kingthy.request.WithdrawReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.service.IncomeService;
import com.kingthy.service.UnionPaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author xumin
 * @Description: 我的收益
 * @DATE Created by 10:25 on 2017/6/12.
 * @Modified by:
 */
@Service
public class IncomeServiceImpl implements IncomeService{

    private static final Logger LOGGER = LoggerFactory.getLogger(IncomeServiceImpl.class);


    @Reference(version = "1.0.0")
    private MemberIncomeDubboService memberIncomeDubboService;

    @Reference(version = "1.0.0")
    private MemberBankCardDubboService memberBankCardDubboService;

    @Reference(version = "1.0.0")
    private OrderItemDubboService orderItemDubboService;

    @Reference(version = "1.0.0")
    private GoodsDubboService goodsDubboService;

    @Reference(version = "1.0.0")
    private MemberIncomeDetailDubboService memberIncomeDetailDubboService;

    @Reference(version = "1.0.0")
    private MemberWithdrawsCashDubboService memberWithdrawsCashDubboService;

    @Reference(version = "1.0.0")
    private WithdrawsCashLogDubboService withdrawsCashLogDubboService;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UnionPaymentService unionPaymentService;


    private String getMember(String token)
    {
        return stringRedisTemplate.opsForValue().get(token);
    }

    /**
     * 收益记录
     * @param pageNo
     * @param pageSize
     * @param token
     * @return
     * @throws Exception
     */
    @Override
    public WebApiResponse<?> incomeList(Integer pageNo, Integer pageSize, String token) throws Exception {

        String memberUuid = getMember(token);

        if (StringUtils.isEmpty(memberUuid)){
            return WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
        }

        int offset = (pageNo <= 0 ? pageNo : pageNo - 1) * pageSize;

        List<IncomeDetailDTO> list = memberIncomeDetailDubboService.selectIncomeList(memberUuid, offset, pageSize);
        //查询商品名称
        List<String> productIds=new ArrayList<>();
        for(IncomeDetailDTO dto:list){
            productIds.add(dto.getGoodsUuid());
        }
        List<Goods> goodsList= goodsDubboService.getGoodsListByIds(productIds);
        for(IncomeDetailDTO dto:list){
            for(Goods goods:goodsList){
                if(dto.getGoodsUuid().equals(goods.getUuid())){
                    dto.setGoodsName(goods.getGoodsName());
                }
            }
        }

        return WebApiResponse.success(list);
    }

    /**
     * 查询提现记录列表
     * @param pageNo
     * @param pageSize
     * @param token
     * @return
     * @throws Exception
     */
    @Override
    public WebApiResponse<?> withdrawsCashList(Integer pageNo, Integer pageSize, String token) throws Exception {

        String memberUuid = getMember(token);

        if (StringUtils.isEmpty(memberUuid)){
            return WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
        }

        int offset = (pageNo <= 0 ? pageNo : pageNo - 1) * pageSize;

        List<WithDrawsCashDTO> list = memberWithdrawsCashDubboService.selectWithdrawsCashList(memberUuid, offset, pageSize);

        return WebApiResponse.success(list);
    }

    /**
     * 查询当前余额
     * @param token
     * @return
     * @throws Exception
     */
    @Override
    public WebApiResponse<?> queryBalance(String token) throws Exception {

        String memberUuid = getMember(token);

        if (StringUtils.isEmpty(memberUuid)){
            return WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
        }

        return WebApiResponse.success(memberIncomeDubboService.queryBalance(memberUuid));
    }

    /**
     * 查询绑定的银行卡
     * @param token
     * @return
     * @throws Exception
     */
    @Override
    public WebApiResponse<?> queryBankInfo(String token) throws Exception {

        String memberUuid = getMember(token);
        if(StringUtils.hasText(memberUuid)){

            IncomeBalanceDTO incomeBalanceDTO = memberBankCardDubboService.queryBankInfoByMembersUuid(memberUuid);
            if (incomeBalanceDTO != null){
                incomeBalanceDTO.setLimit(50000L);
                incomeBalanceDTO.setSingleLimit(5000L);
            }
            return WebApiResponse.success(incomeBalanceDTO);
        }else{
            return  WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
        }
    }

    /**
     * 增加收益
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    public WebApiResponse<?> addIncome(IncomeAddReq req) throws Exception {

        String orderSn = req.getOrderSn();

        //查询订单信息
        IncomeDetailDTO.OrderItem orderItem = orderItemDubboService.selectOrderItemInfo(orderSn);

        //订单号不正确
        if (orderItem == null) {
            return WebApiResponse.error(WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
        }

        //查询此订单是否已经增加过收益
        if (memberIncomeDetailDubboService.countIncomeDetailByOrderSn(orderSn) > 0) {
            return WebApiResponse.error(WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
        }
        req.setAmount(orderItem.getAmount());
        req.setGoodsUuid(orderItem.getGoodsUuid());
        req.setQuantity(orderItem.getQuantity());

        //新增收益
        int res=memberIncomeDubboService.addIncome(req);
        if(res>0){
            return WebApiResponse.success();
        }else{
            return WebApiResponse.error("执行失败！");
        }
    }

    /**
     * 会员绑定银行卡
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    public WebApiResponse<?> bindingBankCard(BindingBankReq req) throws Exception {

        String membersUuid = getMember(req.getToken());

        if (StringUtils.isEmpty(membersUuid)){
            return WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
        }

        if (memberBankCardDubboService.countBankCardByCardNoAndMemberUuid(req.getCardNo(), membersUuid) > 0){
            return WebApiResponse.error(WebApiResponse.ResponseMsg.PARAMETER_ERROR.getValue());
        }

        MemberBankCard bankCard = new MemberBankCard();

        bankCard.setBankCode(req.getBankCode());
        bankCard.setBankName(req.getBankName());
        bankCard.setCardNo(req.getCardNo());
        bankCard.setCardholder(req.getCardholder());
        bankCard.setIdentityCard(req.getIdentityCard());
        bankCard.setPhone(req.getPhone());
        bankCard.setMembersUuid(membersUuid);
        bankCard.setCreateDate(new Date());
        bankCard.setCreator(membersUuid);
        bankCard.setVersion(0);
        bankCard.setDelFlag(false);

        int result = memberBankCardDubboService.insert(bankCard);

        return result > 0 ? WebApiResponse.success() : WebApiResponse.error("绑定银行卡失败");
    }

    /**
     * 提现到银行卡
     * @param req
     * @return
     */
    @Override
    public WebApiResponse<?> withdraw(WithdrawReq req) throws Exception {

        String membersUuid = getMember(req.getToken());
        req.setMembersUuid(membersUuid);
        if (StringUtils.isEmpty(membersUuid)){
            return WebApiResponse.error(WebApiResponse.ResponseMsg.TOKEN_ERROR.getValue());
        }
        /**
         * 查询绑定的银行卡
         */
        MemberBankCard card = memberBankCardDubboService.selectBankCardInfo(req.getBankCardUuid());
        req.setCardNo(card.getCardNo());

        //提现
        String cashUuid=memberIncomeDubboService.withdraw(req);


        if (StringUtils.hasText(cashUuid)){

            //发送消息到队列
            WithdrawsCashMqDTO cashDTO = new WithdrawsCashMqDTO();
            cashDTO.setCreateDate(new Date());
            cashDTO.setMemberUuid(membersUuid);
            cashDTO.setWithdrawsUuid(cashUuid);
            cashDTO.setAccNo(card.getCardNo());
            cashDTO.setAmount(Math.round(req.getMoney() * 10 * 10));
            cashDTO.setCertifId(card.getIdentityCard());
            cashDTO.setOrderId(cashUuid);
            rabbitTemplate.convertAndSend(RabbitmqConstants.WithdrawsCashEnum.EXCHANGE.getValue(),
                    RabbitmqConstants.WithdrawsCashEnum.ROUTING.getValue(), cashDTO);
        }

        return WebApiResponse.success();
    }
    /**
     * 更新提现状态及流水号
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    public WebApiResponse<?> updateStatusAndOrderId(UpdateWithDrawStatusReq req) throws Exception {

        int res=memberIncomeDubboService.updateStatusAndOrderId(req);
        if(res>0){
            return WebApiResponse.success();
        }else{
            return WebApiResponse.error(WebApiResponse.ResponseMsg.FAIL.getValue());
        }
    }

    /**
     * 定时任务 查询金额是否到账 并 更新数据库
     * @return
     * @throws Exception
     */
    @Override
    public void updateStatusTask() throws Exception {

        //查询是否有提现中的记录
        List<MemberWithdrawsCash> list = memberWithdrawsCashDubboService.selectWithdrawsCashListByStatus(MemberWithdrawsCash.StatusType.C_1.getValue());

        if (list.isEmpty()){
            return ;
        }

        //启用多线程查询提现结果
        ExecutorService executorService = Executors.newFixedThreadPool(list.size() < 10 ? list.size() : 10);

        //提现成功的uuid
        List<String> listUuid = new ArrayList<>();

        try{

            List<Callable<WebApiResponse<?>>> callables = new ArrayList<>();

            list.forEach(vo -> callables.add(() -> unionPaymentService.queryTranStatus(vo.getUuid())));

            List<Future<WebApiResponse<?>>> futureList = executorService.invokeAll(callables);

            futureList.forEach(future -> {
                try {
                    if (future.get().getCode() == WebApiResponse.SUCCESS_CODE
                            && future.get().getData() != null){
                        listUuid.add(future.get().getData().toString());
                    }
                } catch(InterruptedException e){

                    LOGGER.error("线程中断异常" + e);
                    Thread.currentThread().interrupt();
                } catch(ExecutionException e){
                    LOGGER.error("连接池异常" + e);
                }
            });

        }finally {
            executorService.shutdown();
        }

        if (listUuid.isEmpty()){
            return;
        }

        memberWithdrawsCashDubboService.updateStatusSuccess(listUuid);
    }

}
