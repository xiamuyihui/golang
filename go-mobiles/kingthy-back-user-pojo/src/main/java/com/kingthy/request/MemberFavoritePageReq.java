package com.kingthy.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 *
 * MemberFavoritePageReq
 * @author likejie
 * @date  2017/9/13.
 *
 */
@Data
@ToString
public class MemberFavoritePageReq implements Serializable {

    private String token;
    @JsonIgnore
    private String memberUuid;

    private Integer pageSize;

    private Integer pageNum;
}
