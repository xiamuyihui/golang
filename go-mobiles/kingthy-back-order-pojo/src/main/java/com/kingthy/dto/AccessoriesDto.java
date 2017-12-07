package com.kingthy.dto;
import com.kingthy.entity.Accessories;
import lombok.Data;

/**
 * AccessoriesDto
 * <p>
 * @author yuanml
 * 2017/5/5
 *
 * @version 1.0.0
 */
@Data
public class AccessoriesDto extends Accessories
{
    /**
     * 查询辅料返回面料分类名称
     */
    private String materielName;
}
