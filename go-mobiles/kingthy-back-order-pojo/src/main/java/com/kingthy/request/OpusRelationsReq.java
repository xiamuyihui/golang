package com.kingthy.request;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * OpusRelationsReq(作品关联)
 * 
 * @author yuanml 2017年4月11日 下午7:47:10
 * 
 * @version 1.0.0
 *
 */
@Data
public class OpusRelationsReq implements Serializable
{
    private String opusUuid;
    
    private String[] relationUuids;
}