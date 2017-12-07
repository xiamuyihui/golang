package com.kingthy.service;

import com.kingthy.entity.Member;


/**
 * 登录
 * @author py
 */
public interface LoginService
{
    /**
     * 根据名称查找用户
     * @param  userName
     * @param  mobile
     * @return
     */
    Member findUserByName(String userName, String mobile);

    /**
     * 修改用户
     * @param member
     * @return
     */
    int updateUser(Member member);
}
