package com.kingthy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xumin
 * @Description: 风格
 * @DATE Created by 19:18 on 2017/5/18.
 * @Modified by:
 */
@Data
@ToString
public class StyleDTO implements Serializable {

    private String styleUuid;
    private String styleName;
}
