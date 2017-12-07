/**
 * 系统项目名称
 * com.kingthy.platform.dto.opus
 * OpusRelationsDto.java
 * 
 * 2017年4月18日-上午10:03:28
 *  2017金昔科技有限公司-版权所有
 *
 */
package com.kingthy.request;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * OpusRelationsDto作品面料辅料工艺等返回参数
 * 
 * @author yuanml 2017年4月18日 上午10:03:28
 * 
 * @version 1.0.0
 *
 */
@Data
public class OpusRelationsDto implements Serializable
{
    private String uuid;
    
    private String name;
    
    private String image;
}
