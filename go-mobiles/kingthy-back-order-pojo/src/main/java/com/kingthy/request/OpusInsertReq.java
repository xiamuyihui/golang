/**
 * 系统项目名称
 * com.kingthy.platform.dto.opus
 * OpusInsertReq.java
 * <p>
 * 2017年4月13日-上午9:41:49
 * 2017金昔科技有限公司-版权所有
 */
package com.kingthy.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * OpusInsertReq(作品传入参数)
 *
 * @author yuanml 2017年4月13日 上午9:41:49
 *
 * @version 1.0.0
 *
 */
@Data
@ToString
public class OpusInsertReq implements Serializable {
    private String uuid;

    private String opusName;

    private String remark;

    private String opusImage;

    private String opusDetails;

    private String modlePath;

    private Boolean isShow;

    @ApiModelProperty(value = "作品风格")
    private String opusStyleUuid;

    @ApiModelProperty(value = "作品季节")
    private String opusSeasonUuid;

    private String memberUuid;

    @ApiModelProperty(value = "作品分类")
    private String opusCategoryUuid;

    @ApiModelProperty(value = "作品部件")
    private String[] opusPartUuids;

    @ApiModelProperty(value = "作品标签")
    private String[] opusTagUuids;

    @ApiModelProperty(value = "作品面料")
    private String[] opusMaterialUuids;

    @ApiModelProperty(value = "作品辅料")
    private String[] opusAccessoriesUuids;
}
