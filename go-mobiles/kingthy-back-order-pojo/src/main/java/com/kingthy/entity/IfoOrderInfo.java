package com.kingthy.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @author:xumin
 * @Description:
 * @Date:18:04 2017/11/2
 */
@Data
@ToString
public class IfoOrderInfo implements Serializable {

    @javax.persistence.Id
    private Integer id;

    private String orderSn;

    private String memberName;

    private Date createOrderDate;

    private Date delivery;

    private BigDecimal amount;

    private BigDecimal freight;

    private Boolean isInvoice;

    private Integer invoiceType;

    private String invoiceTitle;

    private Integer paymentMethodType;

    private Boolean paymentStatus;

    private String phone;

    private String address;

    private Integer provinceUuid;

    private String provinceName;

    private Integer cityUuid;

    private String cityName;

    private Integer areaUuid;

    private String areaName;

    private String street;

    private String detailAddress;

    private String zipCode;

    private String consignee;

    private String memo;

    private Date modifyDate;

    private Date createDate;

    private String updateId;

    private Integer ifStatus;

    private Integer operSt;

    private String distId;


    private static final long serialVersionUID = 1L;


    /**
     * 状态 0初始、4同步中、8同步完成可删除，10同步失败
     */
    public enum StatusType
    {

        /**
         * @Author:xumin
         * @Description:
         * @Date:16:51 2017/11/2
         */
        init(0),
        /**
         * @Author:xumin
         * @Description:
         * @Date:16:51 2017/11/2
         */
        sync(4),
        /**
         * @Author:xumin
         * @Description:
         * @Date:16:51 2017/11/2
         */
        success(8),
        /**
         * @Author:xumin
         * @Description:
         * @Date:16:51 2017/11/2
         */
        fail(10);
        private int value;

        public int getValue()
        {
            return value;
        }

        public void setValue(int value)
        {
            this.value = value;
        }

        private StatusType(int value)
        {
            this.value = value;
        }

    }

    /**
     * 操作区分，0新增，4修改，8删除
     */
    public enum OperStType
    {
        /**
         * @Author:xumin
         * @Description:
         * @Date:16:51 2017/11/2
         */
        add(0),
        /**
         * @Author:xumin
         * @Description:
         * @Date:16:51 2017/11/2
         */
        update(4),
        /**
         * @Author:xumin
         * @Description:
         * @Date:16:51 2017/11/2
         */
        del(8);

        private int value;

        public int getValue()
        {
            return value;
        }

        public void setValue(int value)
        {
            this.value = value;
        }

        private OperStType(int value)
        {
            this.value = value;
        }
    }

}