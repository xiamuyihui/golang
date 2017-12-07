package com.kingthy.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kingthy.dubbo.user.service.MemberDubboService;
import com.kingthy.entity.Member;
import com.kingthy.service.LoginService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service(value = "userService")

/**
 * 登录
 * @author py
 */
public class LoginServiceImpl implements LoginService
{
    private static final Logger LOG= LoggerFactory.getLogger(LoginServiceImpl.class);
    @Reference(version = "1.0.0",timeout = 3000,retries = 0)
    private MemberDubboService memberDubboService;

    @HystrixCommand(fallbackMethod = "findUserByNameFallback")
    @Override
    public Member findUserByName(String userName, String phone)
    {
        Member member = new Member();
        member.setPhone(phone);
        member=memberDubboService.selectOne(member);
        return member;
        
    }
    @HystrixCommand(fallbackMethod = "updateUserFallback")
    @Override
    public int updateUser(Member member)
    {
        return memberDubboService.updateByUuid(member);
    }


    /**熔断处理****/
    private  Member findUserByNameFallback(String userName, String phone,Throwable e){
        LOG.error("findUserByName 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }
    private  int updateUserFallback(Member member,Throwable e)  {
        LOG.error("updateUser 发生异常，进入熔断，异常信息：" + e.toString());
        throw new RuntimeException(e);
    }
}
