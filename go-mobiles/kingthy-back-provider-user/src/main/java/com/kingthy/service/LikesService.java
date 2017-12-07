package com.kingthy.service;

import com.kingthy.entity.Likes;

/**
 * likesService(描述其作用)
 * <p>
 * @author  赵生辉 2017年05月17日 15:55
 *
 * @version 1.0.0
 */
public interface LikesService
{
    /**
     *
     * 新增
     * @param likes
     * @return
     */
    int createLike(Likes likes);
    /**
     *
     * 检查
     * @param likes
     * @return
     */
    boolean checkLike(Likes likes);
    /**
     *
     * 删除
     * @param likes
     * @return
     */
    int deleteLike(Likes likes);
}
