package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name ReportUserUuidByAgeDto
 * @description 用户不同年龄段uuid参数
 * @create 2017/8/28
 */
@Data
@ToString
public class ReportUserUuidByAgeDto implements Serializable {
    private String age;
    private String uuid;
}
