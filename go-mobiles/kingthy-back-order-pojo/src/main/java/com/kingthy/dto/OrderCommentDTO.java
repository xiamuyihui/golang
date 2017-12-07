package com.kingthy.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kingthy.common.BaseReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 16:06 on 2017/5/11.
 * @Modified by:
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class OrderCommentDTO extends BaseReq implements Serializable {

    @ApiModelProperty("图片列表")
    private List<String> listFiles;

    @ApiModelProperty("是否匿名")
    private Boolean anonymousFlag;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("订单号")
    private String orderSn;

}
