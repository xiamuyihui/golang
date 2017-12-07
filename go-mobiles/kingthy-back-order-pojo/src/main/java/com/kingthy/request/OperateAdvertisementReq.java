package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name OperateAdvertisementReq
 * @description 广告入参封装类
 * @create 2017/8/9
 */
@Data
@ToString
public class OperateAdvertisementReq implements Serializable{

    private Status status;

    /**
     * 状态
     */
    public enum Status{
        /**
         * @Author:xumin
         * @Description:
         * @Date:16:56 2017/11/2
         */
        up(true,"上架"),
        /**
         * @Author:xumin
         * @Description:
         * @Date:16:56 2017/11/2
         */
        down(false,"下架");
        private boolean value;
        private String nameValue;
        private Status(boolean value,String nameValue){
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

    private String adName;

    private String description;

    private Date startTime;

    private Date endTime;

    private int pageNum;

    private int pageSize;

    private String banners;

    private String href;

    private String length;

    private String width;

    private String location;
}
