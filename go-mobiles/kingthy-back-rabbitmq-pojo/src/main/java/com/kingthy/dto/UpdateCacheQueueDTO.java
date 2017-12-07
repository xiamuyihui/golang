package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * 更新缓存队列对象
 *
 * @author Created by likejie on 2017/5/27.
 */
@Data
@ToString
public class UpdateCacheQueueDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**缓存key****/
    private String cacheKey;


    /**参数对象***/
    private String data;

}
