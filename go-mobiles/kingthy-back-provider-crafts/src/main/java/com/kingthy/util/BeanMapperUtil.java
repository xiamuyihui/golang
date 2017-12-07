package com.kingthy.util;

import org.springframework.cglib.beans.BeanCopier;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by KingThy on 2017/4/25.
 */
public class BeanMapperUtil {

    public static ConcurrentHashMap<String,BeanCopier>  beanCopierMap=new  ConcurrentHashMap<String,BeanCopier>();

       public static void copyProperties(Object source,Object target){
           String beankey=generateKey(source.getClass(),target.getClass());
           BeanCopier copier=BeanCopier.create(source.getClass(),target.getClass(),false);
           beanCopierMap.putIfAbsent(beankey,copier);
           copier=beanCopierMap.get(beankey);
           copier.copy(source,target,null);
       }
       private static String generateKey(Class<?> class1,Class<?> class2){
           return class1.toString()+class2.toString();
       }
}
