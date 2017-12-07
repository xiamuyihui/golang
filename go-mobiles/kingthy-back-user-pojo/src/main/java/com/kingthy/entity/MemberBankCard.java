package com.kingthy.entity;

import com.kingthy.common.BaseTableFileds;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * MemberBankCard
 * @author likejie
 * @date  2017/6/8.
 */
@Data
@ToString
public class MemberBankCard extends BaseTableFileds implements Serializable {

    private String membersUuid;

    private String cardNo;

    private String bankName;

    private String bankCode;

    private String cardholder;

    private String identityCard;

    private String phone;

    private static final long serialVersionUID = 1L;

}