package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 *
 * QueryCollocationReq(描述其作用)
 * @author 赵生辉
 * @date  2017年05月19日 14:59
 *
 */
@Data
@ToString
public class QueryCollocationReq implements Serializable
{
    private Integer pageNo;

    private Integer pageSize;

    private String temperatureUuid;

    private String occasionUuid;

    private String colourUuid;

    private String styleUuid;

    private static final long serialVersionUID = 1L;
}
