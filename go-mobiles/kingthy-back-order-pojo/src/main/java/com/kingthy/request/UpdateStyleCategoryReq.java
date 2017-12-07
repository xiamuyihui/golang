package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * UpdateStyleCategoryReq(描述其作用)
 * <p>
 * @author 赵生辉 2017年07月13日 10:53
 *
 * @version 1.0.0
 */
@Data
@ToString
public class UpdateStyleCategoryReq implements Serializable
{
    private String uuid;

    private String className;

    private Boolean status;

    private String description;

    private static final long serialVersionUID = 1L;
}
