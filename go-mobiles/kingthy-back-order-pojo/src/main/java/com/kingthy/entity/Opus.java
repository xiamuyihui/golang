package com.kingthy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 *
 * Opus(作品实体类)
 *
 * @author yuanml 2017年4月11日 下午7:42:35
 *
 * @version 1.0.0
 *
 */
@ToString
@Data
public class Opus implements Serializable
{
    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date modifyDate;

    private Integer version;

    private String opusName;

    private String remark;

    private String sn;

    private String modlePath;

    private Integer opusStatus;

    private Boolean isShow;

    private Boolean delFlag;

    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid_short()")
    private String uuid;

    private String memberUuid;

    private String memberNick;

    private String creator;

    private String modifier;

    private String opusMaterialInfo;

    private String opusPartsInfo;

    private String opusAccessoriesInfo;

    private String season;

    private String classCategoryType;

    private String classCategoryName;

    private BigDecimal standardPrice;

    private String opusImage;

    private static final long serialVersionUID = 1L;

    public enum OpusStatus
    {
        /**
         * @Author:xumin
         * @Description:
         * @Date:17:23 2017/11/2
         */
        opusPublish(1, "已发布"), 
        /**
         * @Author:xumin
         * @Description:
         * @Date:17:23 2017/11/2
         */
        opusUnPublish(0, "未发布");
        private int value;

        private String nameValue;

        private OpusStatus(int value, String nameValue)
        {
            this.value = value;
            this.nameValue = nameValue;
        }

        public int getValue()
        {
            return value;
        }

        public void setValue(int value)
        {
            this.value = value;
        }

        public String getNameValue()
        {
            return nameValue;
        }

        public void setNameValue(String nameValue)
        {
            this.nameValue = nameValue;
        }
    }
}