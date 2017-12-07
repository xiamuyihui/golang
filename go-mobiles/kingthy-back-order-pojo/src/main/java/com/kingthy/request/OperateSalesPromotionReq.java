package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name OperateSalesPromotionReq
 * @description 促销请求入参封装类
 * @create 2017/8/8
 */
@Data
@ToString
public class OperateSalesPromotionReq implements Serializable{

    private String activityName;

    private String policy;

    private Date startTime;

    private Date endTime;

    private Integer joinGoods;

    private String description;
    /**
     * 活动类型
     */
    private PolicyType policyType;

    public enum PolicyType{
        /**
         * @Author:xumin
         * @Description:
         * @Date:17:17 2017/11/2
         */
        discount(0,"折扣"),
        /**
         * @Author:xumin
         * @Description:
         * @Date:17:17 2017/11/2
         */
        fullCut(1,"满减");
        private int value;
        private String nameValue;
        private PolicyType(int value,String nameValue){
            this.value = value;
            this.nameValue = nameValue;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getNameValue() {
            return nameValue;
        }

        public void setNameValue(String nameValue) {
            this.nameValue = nameValue;
        }
    }

    private int pageSize;

    private int pageNum;

    /**
     * 活动状态
     */
    public enum Status{
        /**
         * @Author:xumin
         * @Description:
         * @Date:17:22 2017/11/2
         */
        before(0,"未开始"),
        /**
         * @Author:xumin
         * @Description:
         * @Date:17:22 2017/11/2
         */
        ongoing(1,"进行中"),
        /**
         * @Author:xumin
         * @Description:
         * @Date:17:23 2017/11/2
         */
        end(2,"结束");
        private int value;
        private String nameValue;
        private Status(int value,String nameValue){
            this.value = value;
            this.nameValue = nameValue;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getNameValue() {
            return nameValue;
        }

        public void setNameValue(String nameValue) {
            this.nameValue = nameValue;
        }
    }

    private Status status;
}
