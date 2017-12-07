/**
 * 系统项目名称
 * com.kingthy.page
 * PageTm.java
 * 
 * 2017年5月22日-下午5:11:23
 *  2017金昔科技有限公司-版权所有
 *
 */
package com.kingthy.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 以时间戳进行分页
 * 
 * 李克杰 2017年5月22日 下午5:11:23
 * 
 * @version 1.0.0
 *
 */
public class PageT<T> implements Serializable
{
    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     *
     * @since 1.0.0
     */
    
    private static final long serialVersionUID = 1L;
    
    /** 返回最后一条数据的时间戳 ***/
    private Long timespan;
    
    /** 分页大小 ****/
    private int pageSize = 6;
    
    /** 分页查询返回的数据集 ****/
    private List<T> pageData = new ArrayList<T>();
    
    public Long getTimespan()
    {
        return timespan;
    }
    
    public void setTimespan(Long timespan)
    {
        this.timespan = timespan;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public List<T> getPageData()
    {
        return pageData;
    }
    
    public void setPageData(List<T> pageData)
    {
        this.pageData = pageData;
    }
    
}
