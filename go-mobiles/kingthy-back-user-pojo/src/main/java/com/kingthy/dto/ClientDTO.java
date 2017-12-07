package com.kingthy.dto;


import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**

 * 客户端连接对象
 * @author likejie
 * @date  2017/6/8.
 */
@Data
@ToString
public class ClientDTO implements Serializable {


    /**
     * 登录令牌
     */
    private  String token;

    /**
     * 终端类型
     */
    private  String terminalType;


}
