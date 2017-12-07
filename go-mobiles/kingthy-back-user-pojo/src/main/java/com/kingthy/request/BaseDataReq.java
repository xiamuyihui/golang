package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * BaseDataReq
 * @author 赵生辉
 * @date 2017年05月11日 11:17
 *
 */
@Data
@ToString
public class BaseDataReq implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String grade;

    private String parentId;

    private Integer type;

}
