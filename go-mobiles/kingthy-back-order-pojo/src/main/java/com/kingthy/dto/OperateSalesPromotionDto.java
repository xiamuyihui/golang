package com.kingthy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name OperateSalesPromotionDto
 * @description 促销活动信息出参封装类
 * @create 2017/8/9
 */
@Data
@ToString
public class OperateSalesPromotionDto implements Serializable{

    private Integer status;

    private String activityName;

    private Integer policyType;

    private String policy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    private Integer joinGoods;

    private String description;

    /**
     * 表主键
     */
    @JsonIgnore
    private Integer id;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date createDate;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 最后修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date modifyDate;

    /**
     * 最后修改人
     */
    private String modifier;

    /**
     * 版本
     */
    @JsonIgnore
    private Integer version;

    /**
     * 删除标识
     */
    @JsonIgnore
    private Boolean delFlag;

    /**
     * 业务主键
     */
    private String uuid;
}
