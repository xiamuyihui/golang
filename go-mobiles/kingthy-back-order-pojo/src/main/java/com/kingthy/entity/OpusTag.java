package com.kingthy.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
/**
 *
 * (作品标签关联)
 * 
 * @author 赵生辉 2017/5/4 15:40
 * 
 * @version 1.0.0 
 *
 */
@Data
@ToString
public class OpusTag implements Serializable {

    /**
     * 作品业务主键
     */
    private String opusUuid;

    /**
     * 标签业务主键
     */
    private String tagsUuid;

    private static final long serialVersionUID = 1L;
    
}