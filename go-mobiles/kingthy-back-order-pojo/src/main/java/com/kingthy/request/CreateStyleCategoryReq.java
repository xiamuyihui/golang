package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * CreateStyleCategoryReq(描述其作用)
 * <p>
 *@author  赵生辉 2017年07月13日 10:04
 *
 * @version 1.0.0
 */
@Data
@ToString
public class CreateStyleCategoryReq implements Serializable
{
    private String className;

    private Boolean status;

    private String description;

    private static final long serialVersionUID = 1L;
}
