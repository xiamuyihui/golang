package com.kingthy.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name MemberBaseInfoReq
 * @description 用户基本信息查询封装类
 * @create 2017/11/6
 */
@Data
@ToString
public class MemberBaseInfoReq implements Serializable {

    @ApiModelProperty("业务主键")
    private List<String> uuids;
}
