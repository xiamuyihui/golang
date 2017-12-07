package com.kingthy.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pagehelper.PageInfo;

import lombok.Data;
import lombok.ToString;

/**
 * GoodsIncrement
 * <p>
 * 2017/11/16
 *
 * @author yuanml
 * @version 1.0.0
 */
@ToString
@Data
public class GoodsIncrement extends PageInfo
{
    
    /**
     * 返回的数据最小更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date referenceTime;
    
}