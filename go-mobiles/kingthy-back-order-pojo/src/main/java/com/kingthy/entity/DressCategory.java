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
 * @Date:17:46 2017/11/2
 */
@Data
@ToString
public class DressCategory implements Serializable {
    private Integer id;

    private Date createDate;

    private Date modifyDate;

    private Integer version;

    private Integer orders;

    private Integer grade;

    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid_short()")
    private String uuid;

    private String parentId;

    private String className;

    private Integer type;

    private Boolean status;

    private String description;

    private String creator;

    private String modifier;

    private Boolean delFlag;

    private Integer goodsNum;

    private static final long serialVersionUID = 1L;

}