package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 渲染视频
 * @author Created by likejie on 2017/10/11.
 */
@Data
@ToString
public class RenderVideoDTO  implements Serializable{

    private String message;
    /**视频访问地址*/
    private String url;

}
