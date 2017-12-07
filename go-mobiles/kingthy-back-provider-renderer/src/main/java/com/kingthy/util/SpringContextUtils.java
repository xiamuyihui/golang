/**
 * 系统项目名称
 * com.kingthy
 * SpringContextUtils.java
 * 
 * 2017年5月23日-下午4:16:21
 *  2017金昔科技有限公司-版权所有
 *
 */
package com.kingthy.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *
 * SpringContextUtils
 * 
 * 李克杰 2017年5月23日 下午4:16:21
 * 
 * @version 1.0.0
 *
 */
@Component
public class SpringContextUtils implements ApplicationContextAware
{
    private static ApplicationContext applicationContext;
    
    @Override
    public void setApplicationContext(ApplicationContext appcontext)
        throws BeansException
    {
        applicationContext = appcontext;
    }
    
    public static ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }

    public static Object getBeanByClass(Class c)
    {
        
        return applicationContext.getBean(c);
    }
}
