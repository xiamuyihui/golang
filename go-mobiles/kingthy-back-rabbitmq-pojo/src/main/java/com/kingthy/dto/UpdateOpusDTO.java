package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * UpdateOpusDTO(描述其作用)
 * <p>
 * @author 赵生辉 2017年08月22日 9:48
 *
 * @version 1.0.0
 */
@Data
@ToString
public class UpdateOpusDTO implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String uuid;

    private Integer status;

    private String memberUuid;
}
