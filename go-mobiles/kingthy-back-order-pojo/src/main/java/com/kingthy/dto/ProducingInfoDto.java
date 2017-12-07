package com.kingthy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 15:49 on 2017/7/18.
 * @Modified by:
 */

@Data
@ToString
public class ProducingInfoDto implements java.io.Serializable{

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date producingDate;

    private String memo;

}
