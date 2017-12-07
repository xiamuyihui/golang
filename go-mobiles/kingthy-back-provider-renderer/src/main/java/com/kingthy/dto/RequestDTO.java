/**
 * 系统项目名称
 * com.kingthy.dto
 * RequestDTO.java
 * 
 * 2017年5月22日-下午2:59:31
 *  2017金昔科技有限公司-版权所有
 *
 */
package com.kingthy.dto;

import java.io.Serializable;

/**
 *
 * RequestDTO
 * 
 * 李克杰 2017年5月22日 下午2:59:31
 * 
 * @version 1.0.0
 *
 */
public class RequestDTO implements Serializable
{
    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     *
     * @since 1.0.0
     */
    
    private static final long serialVersionUID = 1L;
    
    /** 登录令牌 ***/
    private String token;
    
    /** 当前的时间戳 ***/
    private long currentTimespan;
    
    /** 文件id ***/
    private String fileId;
    
    public String getToken()
    {
        return token;
    }
    
    public void setToken(String token)
    {
        this.token = token;
    }
    
    public long getCurrentTimespan()
    {
        return currentTimespan;
    }
    
    public void setCurrentTimespan(long currentTimespan)
    {
        this.currentTimespan = currentTimespan;
    }
    
    public String getFileId()
    {
        return fileId;
    }
    
    public void setFileId(String fileId)
    {
        this.fileId = fileId;
    }
    
}
