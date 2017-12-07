/**
 * 系统项目名称
 * com.kingthy.platform.entity.basedata
 * Season.java
 * 
 * 2017年3月29日-下午2:06:06
 *  2017金昔科技有限公司-版权所有
 *
 */
package com.kingthy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kingthy.common.BaseTableFileds;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * MaterielCategory(物料分类实体类)
 * 
 * @author yuanml 2017年3月29日 下午2:06:06
 * 
 * @version 1.0.0
 *
 */
@ToString
@Data
@EqualsAndHashCode(callSuper = true)
public class MaterielCategory extends BaseTableFileds
{
    private Integer parentId;

    private String className;

    private Boolean status;

    private String description;
    
    @JsonIgnore
    private Integer orders;
    
    private Integer grade;
    
    @JsonIgnore
    private String treePath;

    private Integer goodsNum;

    private String leaf;

    private String code;

    private Integer cippRecoedId;
    
    private static final long serialVersionUID = 1L;
}