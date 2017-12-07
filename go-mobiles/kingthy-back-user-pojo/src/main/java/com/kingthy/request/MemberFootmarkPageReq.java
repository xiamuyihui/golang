package com.kingthy.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 *
 * MemberFootmarkPageReq
 * @author likejie
 * @date  2017/9/13.
 *
 */
@Data
@ToString
public class MemberFootmarkPageReq implements Serializable {

    @JsonIgnore
    private String memberUuid;
    private Integer pageSize=10;
    private Integer pageNum=0;
}
