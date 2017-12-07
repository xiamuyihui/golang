package com.kingthy.request;

import lombok.Data;
import lombok.ToString;


/**
 *
 * QueryCommentReq(描述其作用)
 * @author 赵生辉
 * @date  2017年05月19日 14:59
 *
 */
@Data
@ToString
public class QueryCommentReq
{
    private Integer pageNo;

    private Integer pageSize;

    private String momentUuid;

    private String token;
}
