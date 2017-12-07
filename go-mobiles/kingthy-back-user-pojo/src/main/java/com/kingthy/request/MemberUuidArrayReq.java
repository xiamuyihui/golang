package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 *
 * UuidArray(接收uuid的数组封装类)
 * @author 陈钊
 * @date  2017年4月6日 下午3:54:56
 *
 */
@Data
@ToString
public class MemberUuidArrayReq implements Serializable {

    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     *
     * @since 1.0.0
     */

    private static final long serialVersionUID = 1L;

    /**
     * 定义数组来接收uuid
     */
    private String[] array;

    /**
     * 冻结标志
     */
    private Boolean isEnabled;

}
