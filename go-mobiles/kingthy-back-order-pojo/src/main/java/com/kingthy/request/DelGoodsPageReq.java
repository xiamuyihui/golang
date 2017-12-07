package com.kingthy.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
/**
 * @author:xumin
 * @Description:
 * @Date:17:46 2017/11/2
 */
@Data
@ToString
public class DelGoodsPageReq implements Serializable
{
    
    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     *
     * @since 1.0.0
     */
    
    private static final long serialVersionUID = 1L;
    
    private int pageSize;
    
    private int pageNum;
    
    private String searchName;

    @JsonIgnore
    private List<String> memberUuids;
}
