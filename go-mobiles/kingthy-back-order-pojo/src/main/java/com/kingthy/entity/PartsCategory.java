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
 * @Date:18:04 2017/11/2
 */
@Data
@ToString
public class PartsCategory implements Serializable {

    private Integer id;

    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid_short()")
    private String uuid;

    private Date createDate;

    private Date modifyDate;

    private String creator;

    private String modifier;

    private Integer version;

    private String className;

    private Boolean status;

    private String image;

    private Boolean delFlag;

    private String sn;

    private String type;

    private String modelFile;

    private static final long serialVersionUID = 1L;

}