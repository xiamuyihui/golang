package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 17:16 on 2017/11/16.
 * @Modified by:
 */
@Data
@ToString
public class RedisCacheDTO implements java.io.Serializable
{
    private Object value;
}
