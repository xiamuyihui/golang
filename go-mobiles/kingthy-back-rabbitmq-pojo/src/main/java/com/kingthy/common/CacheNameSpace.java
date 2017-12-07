package com.kingthy.common;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 14:56 on 2017/11/15.
 * @Modified by:
 */
public enum CacheNameSpace {
    ORDER_CACHE,

    /**
     * 商品信息缓存
     */
    GOODS,
    /**
     * 商品当月销量
     */
    GOODS_SALE_COUNT,
    /**
     * 商品是否被收藏
     */
    GOODS_FAVORITE,
    /**
     * 商品评论总数
     */
    GOODS_BUYER_SHOW_COUNT,

    /**
     * 设计师信息 是否关注信息
     */
    GOODS_DESIGNER_INFO,
    /**
     * 体型数据
     */
    MEMBER_FIGURE,
    /**
     * 买家秀
     */
    GOODS_BUYERS_SHOW_LIST
}
