package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author chenz
 * @version 1.0.0
 * @class_name PartsFileDto
 * @description 部件文件封装类
 * @create 2017/9/8
 */
@Data
@ToString
public class PartsFileDto implements Serializable {

    private String uuid;

    private String image;

    private String modelFile;
}
