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
public class MaterialMIfo implements Serializable {

    @javax.persistence.Id
    private Integer id;

    private String code;

    private String name;

    private String type;

    private String unit;

    private BigDecimal price;

    private String colorCode;

    private String imageSrc;

    private Integer ifStatus;

    private String updaterId;

    private Date createTime;

    private Date updateTime;

    private Integer operSt;

    private String distId;

    /**
     * 幅宽
     */
    private BigDecimal breadth;
    /**
     * 是否缩水： 0 否，1 是
     */
    private Boolean shrink;

    /**
     * 安全库存
     */
    private Integer safeInventory;

    /**
     * 克重
     */
    private Float weight;

    /**
     * 规格(例：线0.5mm)
     */
    private String specs;
    /**
     * 供应商信息备注
     */
    private String supplierRemark;

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