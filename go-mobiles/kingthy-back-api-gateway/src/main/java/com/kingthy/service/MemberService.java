package com.kingthy.service;

import com.kingthy.dto.UserDTO;
import com.kingthy.entity.Member;

public interface MemberService
{
    /**
     * 用户注册接口 createUser(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
     * 
     * @param userDTO
     * @return int
     * @exception @since 1.0.0
     */
    int createUser(UserDTO userDTO);
    
    /**
     * 更新用户信息 updateUserPwd(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
     * 
     * @param userDTO
     * @return int
     * @exception @since 1.0.0
     */
    int updateUserPwd(UserDTO userDTO);
    
    Member findUserInfoByName(String userId);
}
