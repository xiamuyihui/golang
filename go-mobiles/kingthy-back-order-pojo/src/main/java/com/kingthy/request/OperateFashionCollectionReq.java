package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name OperateFashionCollectionReq
 * @description 潮搭请求入参类
 * @create 2017/8/9
 */
@Data
@ToString
public class OperateFashionCollectionReq implements Serializable {

    private String collectionName;

    private Date startTime;

    private Date endTime;

    private Status status;

    /**
     * 状态
     */
    public enum Status {
        /**
         * @Author:xumin
         * @Description:
         * @Date:17:17 2017/11/2
         */
        up(true, "上架"), 
        /**
         * @Author:xumin
         * @Description:
         * @Date:17:17 2017/11/2
         */
        down(false, "下架");
        private boolean value;
        private String nameValue;

        private Status(boolean value, String nameValue) {
            this.value = value;
            this.nameValue = nameValue;
        }

        public boolean isValue() {
            return value;
        }

        public void setValue(boolean value) {
            this.value = value;
        }

        public String getNameValue() {
            return nameValue;
        }

        public void setNameValue(String nameValue) {
            this.nameValue = nameValue;
        }
    }

    private int pageNum;
    private int pageSize;

    private String banners;

    private String temperature;

    private String occasion;

    private String color;

    private String style;

    private String clothModel;

    private String clothModelPic;
}
