package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 20:01 on 2017/9/14.
 * @Modified by:
 */
@Data
@ToString
public class MaterialAccessoriesDTO implements java.io.Serializable
{
    private String code;
    /**
     * 操作区分，0新增，4修改，8删除
     */
    private Integer operSt;
    private Integer tableType;


    /**
     * material 面料表
     * accessories 辅料表
     */
    public enum TableType
    {
        /**
         * @Author:xumin
         * @Description:
         * @Date:9:51 2017/11/2
         */
        material(1),
        /**
         * @Author:xumin
         * @Description:
         * @Date:9:51 2017/11/2
         */
        accessories(2);

        private int value;

        public int getValue()
        {
            return value;
        }

        public void setValue(int value)
        {
            this.value = value;
        }

        private TableType(int value)
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
         * @Date:9:51 2017/11/2
         */
        add(0),
        /**
         * @Author:xumin
         * @Description:
         * @Date:9:52 2017/11/2
         */
        update(4),
        /**
         * @Author:xumin
         * @Description:
         * @Date:9:52 2017/11/2
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

    private static final long serialVersionUID = 1L;
}
