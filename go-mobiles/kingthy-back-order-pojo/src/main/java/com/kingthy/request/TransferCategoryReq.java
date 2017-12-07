package com.kingthy.request;

import lombok.Data;
import lombok.ToString;
import java.io.Serializable;

/**
 * 
 *
 * TransferCategoryReq(转移分类接口入参封装类)
 * 
 * @author 陈钊 2017年3月31日 下午4:11:02
 * 
 * @version 1.0.0
 *
 */
@Data
@ToString
public class TransferCategoryReq implements Serializable
{
    
    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     *
     * @since 1.0.0
     */
    
    private static final long serialVersionUID = 1L;

    /**
     * 原父节点的uuid
     */
    private String sourceUuid;

    /**
     * 新父节点的uuid
     */
    private String targetUuid;

    /**
     * 原父节点的级别编号
     */
    private int sourceGrade;

    /**
     * 新父节点的级别编号
     */
    private int targetGrade;
    
}
