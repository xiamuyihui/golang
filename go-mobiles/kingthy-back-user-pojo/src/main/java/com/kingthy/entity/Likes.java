package com.kingthy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * Likes
 * @author zsh
 * @date  2017/6/8.
 */
@Data
@ToString
public class Likes implements Serializable {

    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date modifyDate;

    private Integer version;

    private String creator;

    private String modeifier;

    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid_short()")
    private String uuid;

    private Boolean delFlag;

    private String momentUuid;

    private String memberUuid;

    private Integer type;

    private Integer status;

    private static final long serialVersionUID = 1L;

    public enum LikesType
    {

        /** 等待付款 */
        moments(0, "动态"),

        /** 生产中 */
        comment(1, "评论");

        private int value;

        private String nameValue;

        public int getValue()
        {
            return value;
        }

        public void setValue(int value)
        {
            this.value = value;
        }

        private LikesType(int value, String name)
        {
            this.value = value;
            this.nameValue = name;
        }

        public String getNameValue()
        {
            return nameValue;
        }

        public void setNameValue(String nameValue)
        {
            this.nameValue = nameValue;
        }

        public static String keys(int key){
            Map<Integer, String> enumMap = new HashMap(LikesType.values().length);
            for (Likes.LikesType status: Likes.LikesType.values()) {
                enumMap.put(status.getValue(), status.getNameValue());
            }
            return enumMap.get(key);
        }
    }
    public enum LikesStatus
    {

        /** 等待付款 */
        show(0, "显示"),

        /** 生产中 */
        hiden(1, "隐藏");

        private int value;

        private String nameValue;

        public int getValue()
        {
            return value;
        }

        public void setValue(int value)
        {
            this.value = value;
        }

        private LikesStatus(int value, String name)
        {
            this.value = value;
            this.nameValue = name;
        }

        public String getNameValue()
        {
            return nameValue;
        }

        public void setNameValue(String nameValue)
        {
            this.nameValue = nameValue;
        }

        public static String keys(int key){
            Map<Integer, String> enumMap = new HashMap(LikesStatus.values().length);
            for (Likes.LikesStatus status: Likes.LikesStatus.values()) {
                enumMap.put(status.getValue(), status.getNameValue());
            }
            return enumMap.get(key);
        }
    }
}