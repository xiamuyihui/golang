package com.kingthy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Date;


/**

 * Collocation
 * @author likejie
 * @date  2017/6/8.
 */
@Data
@ToString
public class Collocation implements Serializable {

    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date modifyDate;

    private Integer version;

    private String creator;

    private String modifier;

    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid_short()")
    private String uuid;

    private Boolean delFlag;

    private String temperatureUuid;

    private String occasionUuid;

    private String colourUuid;

    private String styleUuid;

    private String indexImage;

    private String goodslist;

    private String description;

    private static final long serialVersionUID = 1L;

}