package com.kingthy.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Date;


/**
 *
 * 走秀视频
 * @author likejie
 * @date  2017/9/11.
 */
@Data
@ToString
public class ShowVideos  implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @ApiModelProperty("业务主键")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT uuid_short()")
    private String uuid;

    private String memberUuid;

    private String name;

    private String videoUrl;

    private Date createDate;

    private Date modifyDate;

    private Integer version;

    private String creator;

    private String modifier;

    private Boolean delFlag;
}
