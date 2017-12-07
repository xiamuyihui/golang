package com.kingthy.service;

import com.github.pagehelper.PageInfo;
import com.kingthy.entity.MemberFavorite;
import com.kingthy.request.MemberFavoritePageReq;
import com.kingthy.request.MemberFavoriteReq;
import com.kingthy.response.MemberFavoriteResp;

import java.util.List;


/**
 * @author :pany <br>
 * @date:2016-11-3 <br>
 *
 */
public interface MemberFavoriteService
{

    /**
     * 获取会员收藏
     * @param memberUuid
     * @return
     */
    List<MemberFavoriteResp> getMemberFavorites(String memberUuid);
    /**
     * 分页查询
     * @param req
     * @return
     */
    PageInfo<MemberFavoriteResp> pageGetMemberFavoriteList(MemberFavoritePageReq req);

    /**
     * 新增
     * @param memberFavorite
     * @return
     */
    int addMemberFavorite(MemberFavorite memberFavorite);

    /**
     * 删除会员收藏
     * @param memberFavoriteReq
     * @return
     */
    int delMemberFacorite(MemberFavoriteReq memberFavoriteReq);

    /**
     * 是否已收藏
     * @param memberFavorite
     * @return
     */
    boolean isFacorited(MemberFavorite memberFavorite);

}
