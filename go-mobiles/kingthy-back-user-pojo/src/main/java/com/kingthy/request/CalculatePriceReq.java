package com.kingthy.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 *
 * 计价请求
 * @author likejie
 * @date  2017/9/11.
 *
 */
@Data
@ToString
public class CalculatePriceReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private String token;
    private String materialUuid;
    private String figureUuid;
}
