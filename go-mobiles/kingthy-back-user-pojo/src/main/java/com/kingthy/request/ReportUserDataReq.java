package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name ReportUserDataReq
 * @description 用户数据模块查询封装类
 * @create 2017/7/24
 */
@Data
@ToString
public class ReportUserDataReq implements Serializable{


    /**数据类型*/
    private DataType dataType;

    /**时间段*/
    private TimeType timeType;

    public enum TimeType{
        /**昨天*/
        yesterday(1,"昨天"),
        /**上周*/
        lastWeek(2,"上周"),
        /**前半个月*/
        lastHalfMonth(3,"前半个月"),
        /**上个月*/
        lastMonth(4,"上个月");
        private int value;
        private String valueName;
        TimeType(int value,String valueName){
            this.value = value;
            this.valueName = valueName;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getValueName() {
            return valueName;
        }

        public void setValueName(String valueName) {
            this.valueName = valueName;
        }
    }

    public enum DataType{
        /**点击量*/
        point(0,"点击量"),
        /**浏览量*/
        view(1,"浏览量"),
        /**注册量*/
        register(2,"注册量"),
        /**登录量*/
        login(3,"登录量"),
        /**试穿量*/
        tryCloth(4,"试穿量"),
        /**下单量*/
        order(5,"下单量"),
        /**购买量*/
        buy(6,"购买量");

        private int value;
        private String nameValue;
        DataType(int value,String nameValue){
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
}
