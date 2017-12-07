package com.kingthy.response;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * CreaterModelResp(描述其作用)
 * <p>
 * 赵生辉 2017年11月24日 11:27
 *
 * @version 1.0.0
 */
@Data
@ToString
public class CreaterModelRes implements Serializable
{
    private static final long serialVersionUID = 1L;

    public String bd;

    public String obj;
}
