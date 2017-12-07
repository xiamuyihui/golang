package com.kingthy.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Date;
/**
 * @author:xumin
 * @Description:
 * @Date:18:14 2017/11/2
 */
@Data
@ToString
public class SegmentCategory implements Serializable {
    private Integer id;

    private Date createDate;

    private Date modifyDate;

    private Integer version;

    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid_short()")
    private String uuid;

    private String creator;

    private String modifier;

    private Boolean delFlag;

    private String className;

    private Integer opusNum;

    private Boolean status;

    private Integer originValue;

    private Integer terminalValue;

    private String description;

    private Integer type;

    private Integer goodsNum;

    private static final long serialVersionUID = 1L;

}