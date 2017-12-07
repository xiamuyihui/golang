package com.kingthy.dto;

import com.kingthy.entity.Material;
import lombok.Data;
import lombok.ToString;
/**
 * @author:xumin
 * @Description:
 * @Date:18:04 2017/11/2
 */
@Data
@ToString
public class MaterialDto extends Material
{

    /**
     * 查询返回面料分类名称
     */
    private String materielName;
}
