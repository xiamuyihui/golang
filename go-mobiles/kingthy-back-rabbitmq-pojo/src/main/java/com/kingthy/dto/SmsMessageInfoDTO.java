package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xumin
 * @Description:
 * @DATE Created by 14:50 on 2017/7/18.
 * @Modified by:
 */

@Data
@ToString
public class SmsMessageInfoDTO extends BaseRabbitMqDTO implements Serializable {

    private String messageUuid;
}
