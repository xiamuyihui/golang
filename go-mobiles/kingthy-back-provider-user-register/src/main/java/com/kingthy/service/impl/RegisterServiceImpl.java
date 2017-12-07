package com.kingthy.service.impl;

import java.util.Date;
import java.util.UUID;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.dubbo.user.service.MemberDubboService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.kingthy.dto.UserRegDTO;
import com.kingthy.entity.Member;
import com.kingthy.service.RegisterService;
import com.kingthy.util.MD5Util;

@Service(value = "registerService")

/**
 * 注册
 * @author py
 */
public class RegisterServiceImpl implements RegisterService
{

    private static final Logger LOG= LoggerFactory.getLogger(RegisterServiceImpl.class);

    @Reference(version = "1.0.0",timeout = 3000,retries = 0)
    private MemberDubboService memberDubboService;


    @HystrixCommand(fallbackMethod = "createUserFallback")
    @Override
    public int createUser(UserRegDTO userDTO)
    {


        Member searchCond=new Member();
        searchCond.setPhone(userDTO.getPhone());
        if (memberDubboService.selectCount(searchCond) > 0)
        {
            return -1;
        }
        Member member = new Member();
        BeanUtils.copyProperties(userDTO, member);
        Date currentDate = new Date();
        String salt = String.valueOf(System.currentTimeMillis());
        String pwd = MD5Util.MD5Encode(userDTO.getPwd() + salt);
        member.setUserName(UUID.randomUUID().toString());
        member.setCreateDate(currentDate);
        member.setLastLoginDate(currentDate);
        member.setRegisterIp(userDTO.getRegIp());
        member.setIsLocked(false);
        member.setIsEnabled(false);
        member.setLoginFailureCount(0);
        member.setLockedDate(null);
        member.setSalt(salt);
        member.setPassWord(pwd);
        member.setModifyDate(currentDate);
        member.setDelFlag(false);
        member.setCreator("system");
        member.setIntegral(1000);
        member.setCreateDate(new Date());
        return memberDubboService.insert(member);
    }
    private  int createUserFallback(UserRegDTO userDTO,Throwable e){
        LOG.error("createUser 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }
    @HystrixCommand(fallbackMethod = "updateUserPwdFallback")
    @Override
    public int updateUserPwd(UserRegDTO userDTO)
    {

        Member searchCond=new Member();
        searchCond.setPhone(userDTO.getPhone());
        Member member = memberDubboService.selectOne(searchCond);
        if (member != null)
        {
            String salt = member.getSalt();
            String pwd = MD5Util.MD5Encode(userDTO.getPwd() + salt);
            member.setPassWord(pwd);
            return memberDubboService.updateByUuid(member);
        }
        return 0;
    }
    private int updateUserPwdFallback(UserRegDTO userDTO,Throwable e){
        LOG.error("updateUserPwd 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }
    @HystrixCommand(fallbackMethod = "findAllMemberFallback")
    @Override
    public PageInfo<Member> findAllMember(int pageNo, int pageSize)
    {
        return memberDubboService.queryPage(pageNo,pageSize,null);
    }
    private PageInfo<Member> findAllMemberFallback(int pageNo, int pageSize,Throwable e){
        LOG.error("findAllMember 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }
    @HystrixCommand(fallbackMethod = "findMemberByUuidFallback")
    @Override
    public Member findMemberByUuid(String uuid)
    {
        return memberDubboService.selectByUuid(uuid);
    }
    private Member findMemberByUuidFallback(String uuid,Throwable e){
        LOG.error("findAllMember 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }
}
