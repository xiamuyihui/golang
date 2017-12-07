package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * QueryShippingMethodPage(描述其作用)
 * <p>
 * @author 赵生辉 2017年07月17日 13:51
 *
 * @version 1.0.0
 */
@Data
@ToString
public class QueryShippingMethodPage implements Serializable
{
    private static final long serialVersionUID = 1L;

    private int pageNum;

    private int pageSize;

    private String name;
}
