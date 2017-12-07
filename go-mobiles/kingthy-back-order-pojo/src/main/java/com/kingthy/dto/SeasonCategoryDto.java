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

import com.kingthy.entity.SeasonCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 *
 * SeasonCategoryDto
 * 
 * @author yuanml 2017年3月31日 下午4:58:32
 * 
 * @version 1.0.0
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SeasonCategoryDto extends SeasonCategory
{
    
    private static final long serialVersionUID = 1L;
    
    private List<SeasonCategoryDto> seasonCategoryDtos;
    
    /**
     * 编辑时传回的上级分类层级
     */
    private String parentGrade;
    
    @Override
    public String toString()
    {
        String result = "{" + "uuid : '" + getUuid() + "'" + ", parentId : '" + getParentId() + "'" + ", className : '"
            + getClassName() + "'" + ", grade : '" + getGrade() + "'";
        
        if (seasonCategoryDtos != null && seasonCategoryDtos.size() != 0)
        {
            result += ", children : " + seasonCategoryDtos.toString();
        }
        else
        {
            result += ", leaf : true";
        }
        
        return result + "}";
    }
    
}
