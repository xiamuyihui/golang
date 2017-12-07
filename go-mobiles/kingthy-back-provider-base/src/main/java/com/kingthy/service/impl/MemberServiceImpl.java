package com.kingthy.service.impl;

import com.kingthy.dto.MemberDTO;
import com.kingthy.entity.Likes;
import com.kingthy.entity.Member;
import com.kingthy.mapper.MemberMapper;
import com.kingthy.service.MemberService;
import com.kingthy.util.BeanMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.List;

/**
 * Created by likejie on 2017/5/26.
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberMapper mapper;

    @Override
    public int addMember(Member member) {
        return mapper.insertSelective(member);
    }

    @Override
    public int deleteMember(String uuid) {
        Example example = new Example(Member.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uuid",uuid);
        return mapper.deleteByExample(example);
    }

    @Override
    public int updateMember(Member member) {
        Example example = new Example(Member.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uuid",member.getUuid());
        return mapper.updateByExampleSelective(member,example);
    }

    @Override
    public List<Member> getMemberList(Member member) {
        return mapper.select(member);
    }

    @Override
    public MemberDTO getMemberByUuid(String uuid) {
        Example example = new Example(Member.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uuid", uuid);
        List<Member> list = mapper.selectByExample(example);
        MemberDTO dto = null;
        if (list.size() > 0)
        {
            dto = new MemberDTO();
            BeanMapperUtil.copyProperties(list.get(0), dto);
        }
        return dto;
    }
}
