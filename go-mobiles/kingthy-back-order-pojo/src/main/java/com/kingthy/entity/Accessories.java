package com.kingthy.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author
 */
@Data
@ToString
public class Accessories implements Serializable {

    private Integer id;

    private Date createDate;

    private Date modifyDate;

    private String creator;

    private String modifier;

    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid_short()")
    private String uuid;

    private Integer version;

    private String code;

    private String name;

    private String specification;

    private String texture;

    private String remark;

    private String supplier;

    private String linkman;

    private String linktel;

    private String linkphone;

    private String faxnum;

    private Integer status;

    private Boolean delFlag;

    private String materielUuid;

    private String measurement;

    private BigDecimal price;

    private Boolean isShrink;

    private Integer isStock;

    private String image;

    private String color;

    private String dataFile;

    private String chartletFile;

    private String  supplierAddress;

    private static final long serialVersionUID = 1L;

    /**
     * 类型
     */
    public enum AccessoriesType
    {
        /**
         * 待采样
         */
        sample(0),
        /**
         * 已仿真
         */
        simulated(1),
        /**
         * 已入库
         */
        warehousing(2);

        private int value;

        public int getValue()
        {
            return value;
        }

        public void setValue(int value)
        {
            this.value = value;
        }

        private AccessoriesType(int value)
        {
            this.value = value;
        }

    }
}