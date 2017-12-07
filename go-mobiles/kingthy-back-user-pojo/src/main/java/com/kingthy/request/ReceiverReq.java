package com.kingthy.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;


import java.io.Serializable;


/**
 *
 * ReceiverReq
 * @author likejie
 * @date  2017/4/20.
 *
 */
@Data
@ToString
public class ReceiverReq implements Serializable
{
    @JsonIgnore
    private String token;
    @JsonIgnore
    private String memberUuid;
    private Integer pageSize=10;
    private Integer pageNum=1;
}
