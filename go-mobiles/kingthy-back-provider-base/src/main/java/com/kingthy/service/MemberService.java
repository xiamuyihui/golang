package com.kingthy.service;


import com.kingthy.dto.MemberDTO;
import com.kingthy.entity.Member;

import java.util.List;

/**
 * Created by likejie on 2017/5/26.
 */
public interface MemberService {

    int addMember(Member member);

    int deleteMember(String uuid);

    int updateMember(Member member);

    List<Member> getMemberList(Member member);

    MemberDTO getMemberByUuid(String uuid);
}
