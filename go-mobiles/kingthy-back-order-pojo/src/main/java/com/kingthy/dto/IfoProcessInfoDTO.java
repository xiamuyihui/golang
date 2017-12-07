package com.kingthy.dto;

import com.kingthy.common.BaseReq;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 14:49 on 2017/9/26.
 * @Modified by:
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class IfoProcessInfoDTO extends BaseReq implements Serializable {

    private String orderItemSn;

    private List<ProcessInfo> processInfoList;

    @Data
    @ToString
    public static class ProcessInfo implements Serializable{

        private String styleCode;

        private String styleTypeCode;

        private String processMemo;

        private Integer processNo;

        private String equipmentType;

        private String componentTypeCode;

        private String partsTypeCode;

        private String stitchingCodes;

        private String piecesCodes;
    }

}
