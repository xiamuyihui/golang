package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author xumin
 * @Description: 面料 材质
 * @DATE Created by 19:18 on 2017/5/18.
 * @Modified by:
 */
@Data
@ToString
public class MaterielAndStyleDTO implements Serializable {

    private List<MaterielDTO> materiel;
    private List<StyleDTO> style;
}
