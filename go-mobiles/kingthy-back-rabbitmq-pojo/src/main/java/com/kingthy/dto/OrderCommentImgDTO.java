package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 14:53 on 2017/5/12.
 * @Modified by:
 */

@Data
@ToString
public class OrderCommentImgDTO extends BaseRabbitMqDTO implements Serializable {
    /**
     * 买家秀评论ID
     */
    private String buyersUuid;
    /**
     * 会员ID
     */
    private String memberUuid;

}
