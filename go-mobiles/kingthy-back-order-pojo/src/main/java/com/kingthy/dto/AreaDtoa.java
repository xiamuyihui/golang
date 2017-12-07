package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * AreaDto(描述其作用)
 * <p>
 * @author 赵生辉 2017年07月21日 10:47
 *
 * @version 1.0.0
 */
@Data
@ToString
public class AreaDtoa implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer grade;

    private String name;

    private Long areaParentId;

    private String fullName;
}
