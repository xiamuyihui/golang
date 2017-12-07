package com.kingthy.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 *
 * AttentionReq
 * @author likejie
 * @date 2017/9/21.
 *
 */
@Data
@ToString
public class AttentionReq implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonIgnore
    private String token;

    @JsonIgnore
    @ApiModelProperty("会员业务主键")
    private String memberUuid;

    @ApiModelProperty("关注对象的uuid")
    private String attentionUuid;
}
