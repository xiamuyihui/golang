package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.Map;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 11:09 on 2017/5/27.
 * @Modified by:
 */

@Data
@ToString
public class BaseRabbitMqDTO implements java.io.Serializable{

    private Date createDate;

    private Map<String, String> headers;
}
