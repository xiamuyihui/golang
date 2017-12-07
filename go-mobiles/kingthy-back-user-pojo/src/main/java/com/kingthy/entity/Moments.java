package com.kingthy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * Moments
 * @author none
 * @date  2017/6/8.
 */
@Data
@ToString
public class Moments implements Serializable {
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

    private String image;

    private String context;

    private String memberUuid;

    private Long commentAmount;

    private Long likeAmount;

    private String memberNick;

    private String memberHead;

    private Integer type;

    private String video;

    private Integer review;

    private String reason;

    private String phone;

    private static final long serialVersionUID = 1L;


}