package com.kingthy.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
/**
 *
 * (作品部件关联)
 *
 * @author 赵生辉 2017/5/4 15:41
 *
 * @version 1.0.0
 *
 */
@Data
@ToString
public class OpusPart implements Serializable {

    /**
     * 作品业务主键
     */
    private String opusUuid;

    /**
     * 部件业务主键
     */
    private String partSubUuid;

    private static final long serialVersionUID = 1L;

}