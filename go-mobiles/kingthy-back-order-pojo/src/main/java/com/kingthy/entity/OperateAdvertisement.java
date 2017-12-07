package com.kingthy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kingthy.common.BaseTableFileds;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 广告表实体
 * @author
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class OperateAdvertisement extends BaseTableFileds implements Serializable
{

    private Boolean status;
    
    private String adName;
    
    private String description;
    
    private String length;
    
    private String width;
    
    private String location;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;
    
    private String banners;
    
    private String href;
    
}