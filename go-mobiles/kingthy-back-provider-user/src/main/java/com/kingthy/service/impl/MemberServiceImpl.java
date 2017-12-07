/**
 * 系统项目名称
 * com.kingthy.service.impl
 * MemberServiceImpl.java
 * 
 * 2017年4月18日-下午5:47:03
 *  2017金昔科技有限公司-版权所有
 *
 */
package com.kingthy.service.impl;


import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.cache.RedisManager;
import com.kingthy.common.KryoSerializeUtils;
import com.kingthy.dto.*;
import com.kingthy.dubbo.user.service.MemberBankCardDubboService;
import com.kingthy.dubbo.user.service.MemberDubboService;
import com.kingthy.entity.Member;
import com.kingthy.request.MemberBaseInfoReq;
import com.kingthy.response.WebApiResponse;
import com.kingthy.response.WebApiResponse.ResponseMsg;
import com.kingthy.service.MemberService;
import com.kingthy.util.BeanMapperUtil;
import com.kingthy.util.MD5Util;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 会员业务逻辑处理
 * @author 李克杰 2017年4月18日 下午5:47:03
 * @version 1.0.0
 *
 */
@Service("memberService")
public class MemberServiceImpl implements MemberService
{
    private static final Logger LOGGER= LoggerFactory.getLogger(AttentionServiceImpl.class);

    private static final Pattern PHONE_PATTERN= Pattern.compile("^1[3,4,5,7,8]\\d{9}$");

    private static final Pattern  PASSWORD_PATTERN=Pattern.compile("((?=.*\\d)(?=.*\\D)|(?=.*[a-zA-Z])(?=.*[^a-zA-Z]))^.{8,16}$");

    private static final Pattern  PAY_PASSWORD_PATTERN=Pattern.compile("^[a-zA-Z0-9]{6,16}$");


    @Autowired
    private RedisManager redisManager;

    @Reference(version = "1.0.0")
    private MemberDubboService memberDubboService;

    @Reference(version = "1.0.0")
    private MemberBankCardDubboService memberBankCardDubboService;


    @HystrixCommand(fallbackMethod = "updateMemberFallback")
    @Override
    public int updateMember(MemberDTO dto)
    {

        Member member = new Member();
        BeanMapperUtil.copyProperties(dto,member);
        int res = memberDubboService.updateByUuid(member);
        if(res>0){
            //更新缓存
            String cacheKey= redisManager.generateCacheKey(MemberDTO.class,dto.getUuid());
            UpdateCacheQueueDTO updateCacheQueueDTO=new UpdateCacheQueueDTO();
            updateCacheQueueDTO.setCacheKey(cacheKey);
            redisManager.updateCache(updateCacheQueueDTO);

        }
        return res;
    }
    private int updateMemberFallback(MemberDTO dto,Throwable e){
        LOGGER.error("updateMember 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }

    @HystrixCommand(fallbackMethod = "getMemberByUuIdFallback")
    @Override
    public MemberDTO getMemberByUuId(String uuid) {

        MemberDTO dto=new MemberDTO();
        String cacheKey = redisManager.generateCacheKey(MemberDTO.class, uuid);
        //从缓存读取
        String cacheData = redisManager.get(cacheKey);
        if (StringUtils.isNotBlank(cacheData)) {
            dto = KryoSerializeUtils.deserializationObject(cacheData, MemberDTO.class);
        } else {
            //获取失效时间，如果已经失效，再查询数据库，即使为空直，如果没失效，也不会直接访问数据库
            long expire = redisManager.getExpire(cacheKey);
            if (expire <= 0) {
                Member member = memberDubboService.selectByUuid(uuid);
                BeanMapperUtil.copyProperties(member,dto);
                if(dto!=null) {
                    //绑卡信息
                    IncomeBalanceDTO incomeBalanceDTO = memberBankCardDubboService.queryBankInfoByMembersUuid(uuid);
                    if (incomeBalanceDTO != null) {
                        //设置绑定的银行卡号
                        dto.setBankCard(incomeBalanceDTO.getCardNo());
                        //设置绑定的银行
                        dto.setBankName(incomeBalanceDTO.getBankName());
                    }
                    cacheData=KryoSerializeUtils.serializationObject(dto);
                }
                redisManager.setByValue(cacheKey,cacheData,redisManager.getRandomExpire(10),TimeUnit.DAYS);

            }
        }
        return dto;
    }
    private MemberDTO getMemberByUuIdFallback(String uuid,Throwable e){
        LOGGER.error("getMemberByUuId 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }

    @HystrixCommand(fallbackMethod = "getHomeInfoFallback")
    @Override
    public MemberHomeDTO getHomeInfo(String memberUuid)
    {
        return memberDubboService.getHomeInfo(memberUuid);
    }
    private MemberHomeDTO getHomeInfoFallback(String memberUuid,Throwable e){
        LOGGER.error("getHomeInfo 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }

    @HystrixCommand(fallbackMethod = "updateNickNameFallback")
    @Override
    public WebApiResponse updateNickName(String uuid, String nickName)
    {
        int count = memberDubboService.checkNickNameIsExist(uuid, nickName);
        if (count > 0)
        {
            // 昵称已存在
            return WebApiResponse.error(nickName + "已被占用，请使用其他昵称！");
        }
        else
        {
            int res= memberDubboService.updateNickName(uuid, nickName);
            if(res>0) {
                String cacheKey = redisManager.generateCacheKey(MemberDTO.class, uuid);
                UpdateCacheQueueDTO updateCacheQueueDTO = new UpdateCacheQueueDTO();
                updateCacheQueueDTO.setCacheKey(cacheKey);
                redisManager.updateCache(updateCacheQueueDTO);
                return WebApiResponse.success(ResponseMsg.SUCCESS.getValue());
            }else{
                return WebApiResponse.error(ResponseMsg.FAIL.getValue());
            }
        }
        
    }
    private WebApiResponse updateNickNameFallback(String uuid, String nickName,Throwable e){
        LOGGER.error("updateNickName 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }

    @HystrixCommand(fallbackMethod = "unlockedFallback")
    @Override
    public WebApiResponse<?> unlocked(String phone) throws Exception {

        return memberDubboService.updateLocked(phone) > 0 ? WebApiResponse.success() : WebApiResponse.error("操作失败");
    }
    private WebApiResponse<?> unlockedFallback(String phone,Throwable e){
        LOGGER.error("unlocked 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }

    @HystrixCommand(fallbackMethod = "modifyMobileFallback")
    @Override
    public WebApiResponse<?> modifyMobile(ModifyPhoneDTO dto) {

        Matcher matcher= PHONE_PATTERN.matcher(dto.getPhone());


        //手机号格式是否正确
        if(!matcher.matches()){
            //手机号格式不正确
            return WebApiResponse.error("手机号码格式不正确！");
        }
        //获取缓存的验证码
        String key = "verify-code:" + dto.getPhone();
        String code=redisManager.get(key);
        if(StringUtils.isBlank(code)){
            return WebApiResponse.error("验证码已过期！");
        }
        if(!code.equalsIgnoreCase(dto.getCode())){
            //手机验证码验证失败
            return WebApiResponse.error("验证码错误！");
        }
        int count=memberDubboService.checkPhoneIsExist(dto.getMemberUuid(),dto.getPhone());
        if(count>0){
            //手机号已注册
            return WebApiResponse.error("该手机号已被其他用户注册！");
        }
        //修改手机号
        int res=memberDubboService.modifyPhone(dto.getMemberUuid(),dto.getPhone());
        if(res>0){

            UpdateCacheQueueDTO updateCacheQueueDTO=new UpdateCacheQueueDTO();
            updateCacheQueueDTO.setCacheKey(key);
            redisManager.updateCache(updateCacheQueueDTO);
            return WebApiResponse.success();
        }else{
            return WebApiResponse.error(ResponseMsg.FAIL.getValue());
        }
    }

    private WebApiResponse<?> modifyMobileFallback(ModifyPhoneDTO dto,Throwable e){
        LOGGER.error("modifyMobile 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }
    @HystrixCommand(fallbackMethod = "modifyPasswordFallback")
    @Override
    public WebApiResponse<?> modifyPassword(String uuid, String password) {

        Matcher matcher= PASSWORD_PATTERN.matcher(password);
        if(!matcher.matches()){
            return WebApiResponse.error("密码格式不正确，请输入8-16位的数字和字符，且需包含至少1个字母和字符");
        }
        //密码加密
        String salt = String.valueOf(System.currentTimeMillis());
        String md5Pwd = MD5Util.MD5Encode(password + salt);
        int res=memberDubboService.modifyPassword(uuid,md5Pwd,salt);
        if(res>0){
            String cacheKey = redisManager.generateCacheKey(MemberDTO.class, uuid);
            UpdateCacheQueueDTO updateCacheQueueDTO = new UpdateCacheQueueDTO();
            updateCacheQueueDTO.setCacheKey(cacheKey);
            redisManager.updateCache(updateCacheQueueDTO);
            return WebApiResponse.success();
        }else{
            return WebApiResponse.error(ResponseMsg.FAIL.getValue());
        }
    }
    private WebApiResponse<?> modifyPasswordFallback(String uuid, String password,Throwable e){
        LOGGER.error("modifyPassword 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }


    @HystrixCommand(fallbackMethod = "modifyPaymentPasswordFallback")
    @Override
    public WebApiResponse<?> modifyPaymentPassword(ModifyPayPasswordDTO dto) {

        /**密码格式校验**/
        Matcher matcher= PAY_PASSWORD_PATTERN.matcher(dto.getPaymentPassword());
        if(!matcher.matches()){
            return WebApiResponse.error("密码格式不正确,请输入6-16数字和字母");
        }
        /**再次校验手机号和登录密码**/
        /*WebApiResponse<?> result=verifyPhoneAndLoginPassword(dto);
        if(result.getCode()==WebApiResponse.ERROR_CODE){
            return  result;
        }*/
        /**密码加密，并修改密码**/
        String paymentSalt = String.valueOf(System.currentTimeMillis());
        String md5Pwd = MD5Util.MD5Encode(dto.getPaymentPassword() + paymentSalt);
        int res=memberDubboService.modifyPaymentPassword(dto.getUuid(),md5Pwd,paymentSalt);
        if(res>0){
            //删除验证码
            String key = "verify-code:" + dto.getPhone();
            redisManager.delete(key);
            if(StringUtils.isNotBlank(dto.getUuid())){
                String cacheKey = redisManager.generateCacheKey(MemberDTO.class, dto.getUuid());
                UpdateCacheQueueDTO updateCacheQueueDTO = new UpdateCacheQueueDTO();
                updateCacheQueueDTO.setCacheKey(cacheKey);
                redisManager.updateCache(updateCacheQueueDTO);
            }

            return WebApiResponse.success();
        }else{
            return WebApiResponse.error(ResponseMsg.FAIL.getValue());
        }
    }
    private WebApiResponse<?> modifyPaymentPasswordFallback(ModifyPayPasswordDTO dto,Throwable e) {
        LOGGER.error("modifyPaymentPassword 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }

    @HystrixCommand(fallbackMethod = "verifyPaymentPasswordFallback")
    @Override
    public WebApiResponse<?> verifyPaymentPassword(String uuid, String paymentPassword) {

        Member member= memberDubboService.selectByUuid(uuid);
        if (member!=null) {
            String salt = member.getPaymenSalt();
            String userMd5Pwd = MD5Util.MD5Encode(paymentPassword + salt);
            if (userMd5Pwd.equals(member.getPaymentPassword())) {

                return WebApiResponse.success("验证成功！");

            } else {

                return WebApiResponse.error("支付密码错误！");
            }
        } else {
            return WebApiResponse.error("用户不存在！");
        }

    }

    private WebApiResponse<?> verifyPaymentPasswordFallback(String uuid, String paymentPassword,Throwable e){
        LOGGER.error("verifyPaymentPassword 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }
    @Override
    public WebApiResponse<?> verifyPhoneAndLoginPassword(ModifyPayPasswordDTO dto){


        Matcher matcher= PHONE_PATTERN.matcher(dto.getPhone());
        //手机号格式是否正确
        if(!matcher.matches()){
            //手机号格式不正确
            return WebApiResponse.error("手机号码格式不正确！");
        }
        //获取缓存的验证码
        String key = "verify-code:" + dto.getPhone();
        String code=redisManager.get(key);
        if(StringUtils.isBlank(code)){
            return WebApiResponse.error("验证码已过期！");
        }
        if(!code.equalsIgnoreCase(dto.getCode())){
            //手机验证码验证失败
            return WebApiResponse.error("验证码错误！");
        }
        //验证登录密码
        Member member= memberDubboService.selectByUuid(dto.getUuid());
        if (member!=null) {
            String salt = member.getSalt();
            //用户输入的密码
            String inputPassword = MD5Util.MD5Encode(dto.getPassword() + salt);
            //数据库中的密码
            if (inputPassword.equals(member.getPassWord())) {

                return WebApiResponse.success("验证成功！");

            } else {

                return WebApiResponse.error("登录密码错误！");
            }
        } else {
            return WebApiResponse.error("用户不存在！");
        }
    }

    @Override
    public List<MemberBaseInfoDTO> getMembersBaseInfo(MemberBaseInfoReq memberBaseInfoReq) {
        return memberDubboService.getMembersBaseInfo(memberBaseInfoReq);
    }
}
