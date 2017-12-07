package com.kingthy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * (作品辅料关联)
 * 
 * @author 赵生辉 2017/5/4 15:44
 * 
 * @version 1.0.0 
 *
 */
@Data
@ToString
public class OpusAccessories implements Serializable {

    @JsonIgnore
    private Integer id;

    /**
     * 作品业务主键
     */
    private String opusUuid;

    /**
     * 辅料业务主键
     */
    private String accessoriesUuid;

    private static final long serialVersionUID = 1L;

}