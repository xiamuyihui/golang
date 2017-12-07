package com.kingthy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Date;

/**

 * 基础数据
 * @author likejie
 * @date  2017/6/8.
 */
@Data
@ToString
public class BaseData implements Serializable {

    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
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

    private static final long serialVersionUID = 1L;


}