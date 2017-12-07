package com.kingthy.entity;

import java.io.Serializable;

import com.kingthy.common.BaseEntity;

import lombok.Data;
import lombok.ToString;
/**
 * @author:xumin
 * @Description:
 * @Date:18:04 2017/11/2
 */
@Data
@ToString
public class MemberFavorite extends BaseEntity implements Serializable
{

    
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY, generator = "SELECT uuid_short()")
    private String uuid;
    
    private String favoriteMembersUuid;
    
    private String favoriteGoodsUuid;
    
    private static final long serialVersionUID = 1L;
    
}