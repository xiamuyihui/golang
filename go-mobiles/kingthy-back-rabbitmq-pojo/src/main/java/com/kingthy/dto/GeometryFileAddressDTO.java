package com.kingthy.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name GeometryFileAddressDTO
 * @description 几何组生成文件地址封装类
 * @create 2017/10/17
 */
@Data
@ToString
public class GeometryFileAddressDTO implements java.io.Serializable{

    private String uuid;
    @ApiModelProperty("文件地址")
    private String fileAddress;
    @ApiModelProperty("0:排料 1:裁床 2:纸样")
    private Integer type;

    /**
     * @Author:xumin
     * @Description: 1:裁床 2:纸样
     * @Date:18:14 2017/11/1
     */
    public enum GeometryType
    {
        /**裁床
         * @Author:xumin
         * @Description:
         * @Date:9:51 2017/11/2
         */
        C(1),
        /**纸样
         * @Author:xumin
         * @Description:
         * @Date:9:51 2017/11/2
         */
        P(2);

        private int value;

        public int getValue()
        {
            return value;
        }

        public void setValue(int value)
        {
            this.value = value;
        }

        private GeometryType(int value)
        {
            this.value = value;
        }
    }
}
