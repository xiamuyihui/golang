package com.kingthy.dto;

import com.kingthy.entity.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * OpusDto(描述其作用)
 * <p>
 * @author 赵生辉 2017年05月08日 9:43
 *
 * @version 1.0.0
 */
@Data
public class OpusDto extends Opus
{

    private static final long serialVersionUID = 1L;

    private List<PartsCategory> opusPartList;

    private List<Accessories> opusAccessoriesList;

    private List<Material> opusMaterialList;
}
