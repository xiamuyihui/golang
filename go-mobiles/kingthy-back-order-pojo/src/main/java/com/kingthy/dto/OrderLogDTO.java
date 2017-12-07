package com.kingthy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author Created by KingThy on 2017/4/24.
 */
@Data
@ToString
public class OrderLogDTO implements java.io.Serializable{

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date createDate;

    private String content;

    private String userName;
}
