package com.kingthy.entity;

import com.kingthy.common.BaseTableFileds;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
/**
 * @author:xumin
 * @Description:
 * @Date:18:04 2017/11/2
 */
@Data
@ToString
public class MessageInfo extends BaseTableFileds implements Serializable {

    private String title;

    private String content;

    private String url;

    private Date pushTime;

    private String messagePlatformUuid;

    private String membersUuid;

    private String memberName;

    private String pushTarget;

    private Boolean pushStatus;

}