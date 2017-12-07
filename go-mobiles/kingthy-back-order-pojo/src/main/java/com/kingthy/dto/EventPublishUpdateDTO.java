package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 14:45 on 2017/11/28.
 * @Modified by:
 */
@Data
@ToString
public class EventPublishUpdateDTO implements Serializable
{
    private Date modifyDate;
    private String eventStatus;
    private List<String> uuidList;
}
