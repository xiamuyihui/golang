package com.kingthy.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 潘勇
 * @Data 2017/5/15 18:24.
 */
@ToString
@Data
public class MemberFavoriteReq implements Serializable
{
    private String token;

    @JsonIgnore
    private String memberUuid;

    @JsonIgnore
    private Date modifyDate;

    private List<String> goodsUuid;

    private boolean cleanAll;

}
