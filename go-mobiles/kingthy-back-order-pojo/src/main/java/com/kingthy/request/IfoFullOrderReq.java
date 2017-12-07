package com.kingthy.request;

import com.kingthy.common.BaseReq;
import com.kingthy.entity.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 18:13 on 2017/11/28.
 * @Modified by:
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class IfoFullOrderReq extends BaseReq implements Serializable
{

    private List<IfoOrderDetailBom> bomList;
    private List<IfoOrderStyleFileInfo> styleFileList;
    private List<IfoOrderStitchingInfo> stitchingStyles;
    private IfoOrderInfo ifoOrderInfo;
    private List<IfoOrderInfoDetail> ifoOrderInfoDetails;
}
