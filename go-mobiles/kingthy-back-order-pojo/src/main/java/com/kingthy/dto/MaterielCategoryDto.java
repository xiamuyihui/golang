/**
 * 系统项目名称
 * com.kingthy.platform.dto.basedata
 * SeasonCategoryDto.java
 * 
 * 2017年3月31日-下午4:58:32
 *  2017金昔科技有限公司-版权所有
 *
 */
package com.kingthy.dto;

import com.kingthy.entity.MaterielCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 *
 * MeterielCategoryDto
 * 
 * @author yuanml 2017年4月1日 下午8:58:32
 * 
 * @version 1.0.0
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MaterielCategoryDto extends MaterielCategory
{
    
    private static final long serialVersionUID = 1L;
    
    private List<MaterielCategoryDto> materielCategoryDtos;
    
    /**
     * 编辑时传回的上级分类层级
     */
    private String parentGrade;
    
    @Override
    public String toString()
    {
        String result = "{" + "uuid : '" + getUuid() + "'" + ", parentId : '" + getParentId() + "'" + ", className : '"
            + getClassName() + "'" + ", grade : '" + getGrade() + "'";
        
        if (materielCategoryDtos != null && materielCategoryDtos.size() != 0)
        {
            result += ", children : " + materielCategoryDtos.toString();
        }
        else
        {
            result += ", leaf : true";
        }
        
        return result + "}";
    }
    
}
