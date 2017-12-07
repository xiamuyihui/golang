package com.kingthy.entity;

import com.kingthy.common.BaseTableFileds;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * MemberFitting
 * @author zsh
 * @date  2017/6/8.
 */
@Data
@ToString
public class MemberFitting extends BaseTableFileds implements Serializable {

    private String membersUuid;

    private String goodsUuid;

    private static final long serialVersionUID = 1L;


}