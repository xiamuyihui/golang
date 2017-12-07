package com.kingthy.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
/**
 * @author:xumin
 * @Description:
 * @Date:17:46 2017/11/2
 */
@Data
@ToString
public class ComponentTypeIfi implements Serializable {

    @javax.persistence.Id
    private Integer id;

    private String code;

    private String name;

    private Integer ifStatus;

    private Integer parentId;

    private String updaterId;

    private Date createTime;

    private Date updateTime;

    private String leaf;

    private String path;

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
         * @Date:16:46 2017/11/2
         */
        init(0),
        /**
         * @Author:xumin
         * @Description:
         * @Date:16:46 2017/11/2
         */
        sync(4),
        /**
         * @Author:xumin
         * @Description:
         * @Date:16:46 2017/11/2
         */
        success(8),
        /**
         * @Author:xumin
         * @Description:
         * @Date:16:46 2017/11/2
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
         * @Date:16:46 2017/11/2
         */
        add(0),
        /**
         * @Author:xumin
         * @Description:
         * @Date:16:46 2017/11/2
         */
        update(4),
        /**
         * @Author:xumin
         * @Description:
         * @Date:16:46 2017/11/2
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