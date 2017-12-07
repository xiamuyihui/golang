package com.kingthy.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 *
 * MemberFootmarkReq
 * @author likejie
 * @date  2017/9/13.
 *
 */
@Data
@ToString
public class MemberFootmarkReq implements Serializable {



    @JsonIgnore
    @ApiModelProperty("会员业务主键")
    private String memberUuid;

    @ApiModelProperty("商品业务主建")
    private String productUuid;

}
