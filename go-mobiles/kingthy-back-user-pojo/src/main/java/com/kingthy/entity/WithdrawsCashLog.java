package com.kingthy.entity;

import com.kingthy.common.BaseTableFileds;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * WithdrawsCashLog
 * @author xumin
 * @date  2017/9/11.
 */
@Data
@ToString
public class WithdrawsCashLog extends BaseTableFileds implements Serializable {

    private String membersUuid;

    private String content;

    private String remark;

    private String withdrawsUuid;

    private String cardNo;

    private static final long serialVersionUID = 1L;

}