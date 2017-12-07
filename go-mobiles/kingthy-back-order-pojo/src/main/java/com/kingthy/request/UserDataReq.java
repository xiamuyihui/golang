package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name UserDataReq
 * @description 用户数据查询入参封装类
 * @create 2017/7/19
 */
@Data
@ToString
public class UserDataReq implements Serializable{
    /**
     * 起始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 时间段
     */
    private TimeRage timeRage;

    public enum TimeRage{
        
        /**
         * @Author:xumin
         * @Description:
         * @Date:17:27 2017/11/2
         */
        yesterday(1,"昨天"),
        /**
         * @Author:xumin
         * @Description:
         * @Date:17:27 2017/11/2
         */
        lastWeek(2,"上周"),
        /**
         * @Author:xumin
         * @Description:
         * @Date:17:27 2017/11/2
         */
        lastHalfMonth(3,"前半个月"),
        /**
         * @Author:xumin
         * @Description:
         * @Date:17:27 2017/11/2
         */
        lastMonth(4,"上个月");
        private int value;
        private String valueName;
        private TimeRage(int value,String valueName){
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
}
