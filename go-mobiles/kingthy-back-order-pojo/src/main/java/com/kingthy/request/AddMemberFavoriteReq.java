package com.kingthy.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Created by likejie on 2017/9/21.
 */
@Data
@ToString
public class AddMemberFavoriteReq implements Serializable {

    private String token;

    @JsonIgnore
    private String favoriteMembersUuid;

    private String favoriteGoodsUuid;
}
