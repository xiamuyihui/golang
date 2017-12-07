package com.kingthy.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 9:53 on 2017/7/17.
 * @Modified by:
 */

@Data
@ToString
public class MessagePlatformReq implements Serializable {

    /**
     * 当前页数
     */
    private Integer pageNum;

    /**
     * 每页展示的数量
     */
    private Integer pageSize;

    @ApiModelProperty("编号")
    private String uuid;

    @ApiModelProperty("平台名称")
    private String platformName;

    @ApiModelProperty("平台描述")
    private String platformDesc;

    @ApiModelProperty("开通时间")
    private Long beginTime;
    private Long endTime;

    @ApiModelProperty("状态 0启用1禁用")
    private Long status;

    @Data
    @ToString
    public static class UpdateStatusReq implements Serializable
    {
        public String uuid;
        @ApiModelProperty("用户")
        public String membersUuid;
        @ApiModelProperty("状态 0启用1禁用")
        private Integer status;

    }

    @Data
    @ToString
    public static class EditReq implements Serializable{

        public String uuid;
        @ApiModelProperty("用户")
        public String membersUuid;

        private String platformName;

        private String platformDesc;

        private Long openTime;

        private Integer status;
    }
}
