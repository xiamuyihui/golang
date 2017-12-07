package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * QueryShopReq(描述其作用)
 * <p>
 * @author 赵生辉 2017年07月21日 17:18
 *
 * @version 1.0.0
 */
@Data
@ToString
public class QueryShopPageReq implements Serializable
{
    private int pageNum;

    private int pageSize;

    private Date beginDate;

    private Date endDate;

    private String name;

    private String city;

    private Boolean status;

    private static final long serialVersionUID = 1L;
}
