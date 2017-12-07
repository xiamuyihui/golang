package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * CreateOpusReq(描述其作用)
 * <p>
 * @author 赵生辉 2017年05月04日 16:20
 *
 * @version 1.0.0
 */
@Data
@ToString
public class QueryOpusListReq implements Serializable
{

    private Date createDate;

    private Date modifyDate;

    private String opusName;

    private String remark;

    private String sn;

    private String opusDetails;

    private String modlePath;

    private Integer opusStatus;

    private Boolean isShow;

    private Boolean delFlag;

    private String uuid;

    private String opusStyleUuid;

    private String opusSeasonUuid;

    private String memberUuid;

    private String opusCategoryUuid;

}
