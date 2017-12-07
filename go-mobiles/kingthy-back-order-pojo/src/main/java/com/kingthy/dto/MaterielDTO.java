package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xumin
 * @Description: 面料 材质
 * @DATE Created by 19:18 on 2017/5/18.
 * @Modified by:
 */
@Data
@ToString
public class MaterielDTO implements Serializable {
    private String materielUuid;
    private String materielName;
}
