package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 14:05 on 2017/7/7.
 * @Modified by:
 */
@Data
@ToString
public class ReplyCommentReq implements Serializable {

    private String content;

    private String buyersUuid;

    private String memberUuid;
}
