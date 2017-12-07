package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name BaseStylePageReq
 * @description 服装款式分页查询参数
 * @create 2017/9/7
 */
@Data
@ToString
public class BaseStylePageReq implements Serializable{

    private String styleSn;

    private String styleName;

    private Date startTime;

    private Date endTime;

    private Integer pageNum;

    private Integer pageSize;
}
