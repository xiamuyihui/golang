package com.kingthy.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class Material implements Serializable {
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

    private String component;

    private Float length;

    private Float weight;

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

    private String yarnCount;

    private Boolean isShrink;

    private BigDecimal price;

    private Integer isStock;

    private String washingReq;

    private String image;

    private String color;

    private String dataFile;

    private String chartletFile;

    private String  supplierAddress;

    private static final long serialVersionUID = 1L;

    /**
     * 类型
     */
    public enum MaterialType
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

        private MaterialType(int value)
        {
            this.value = value;
        }

    }
}