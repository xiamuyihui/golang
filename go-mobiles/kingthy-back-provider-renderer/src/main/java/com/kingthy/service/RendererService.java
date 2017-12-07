/**
 * 系统项目名称
 * com.kingthy.service
 * RendererService.java
 * 
 * 2017年5月18日-上午9:48:13
 *  2017金昔科技有限公司-版权所有
 *
 */
package com.kingthy.service;

/**
 *
 * RendererService
 * 
 * 李克杰 2017年5月18日 上午9:48:13
 * 
 * @version 1.0.0
 *
 */
public interface RendererService
{
    
    /**
     * 生成3D模型图
     */
    String generate3Dgraphs(String memberUuid, long timespan, String fileId);
    
    /**
     * 
     * 获取生成进度
     */
    String getGenerateProgress(String key);
}
