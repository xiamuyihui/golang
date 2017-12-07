package com.kingthy.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingthy.dto.UserDTO;
import com.kingthy.entity.Member;
import com.kingthy.entity.TbUser;
import com.kingthy.mapper.MemberMapper;
import com.kingthy.service.MemberService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service(value = "tbUserService")
public class MemberServiceImpl implements MemberService
{
    
    @Autowired
    private transient MemberMapper memberMapper;
    
    @Override
    public int createUser(UserDTO userDTO)
    {
        Member member = new Member();
        BeanUtils.copyProperties(userDTO, member);
        Example example = new Example(TbUser.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("phone", userDTO.getPhone());
        if (memberMapper.selectCountByExample(example) > 0)
        {
            return -1;
        }
        Date currentDate = new Date();
        member.setCreateDate(currentDate);
        member.setLastLoginDate(currentDate);
//        member.setRegisterIp(userDTO.getRegIp());
        member.setIsLocked(false);
        member.setIsEnabled(false);
        member.setLoginFailureCount(0);
        member.setLockedDate(null);
        int result = memberMapper.insert(member);
        return result;
    }
    
    @Override
    public int updateUserPwd(UserDTO userDTO)
    {
        Example example = new Example(TbUser.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("phone", userDTO.getPhone());
        List<Member> tbUsers = memberMapper.selectByExample(example);
        Member member = null;
        if (tbUsers != null && tbUsers.size() > 0)
        {
            member = tbUsers.get(0);
            member.setPassWord(userDTO.getPwd());
            return memberMapper.updateByExample(member, example);
        }
        return 0;
    }
    
    @Override
    public Member findUserInfoByName(String phone)
    {
        // TODO Auto-generated method stub
        Example example = new Example(Member.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("phone", phone);
        List<Member> members = memberMapper.selectByExample(example);
        Member member = null;
        if (members != null && members.size() > 0)
        {
            member = members.get(0);
            return member;
        }
        return null;
    }
    
}
