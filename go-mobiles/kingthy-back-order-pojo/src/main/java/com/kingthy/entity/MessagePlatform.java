package com.kingthy.entity;

import com.kingthy.common.BaseTableFileds;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
/**
 * @author:xumin
 * @Description:
 * @Date:18:04 2017/11/2
 */
@Data
@ToString
public class MessagePlatform extends BaseTableFileds implements Serializable{

    private String platformName;

    private String platformDesc;

    private Date openTime;

    private Integer status;

    private String queueName;

}