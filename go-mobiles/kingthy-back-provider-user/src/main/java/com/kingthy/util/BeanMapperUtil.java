package com.kingthy.util;

import org.springframework.cglib.beans.BeanCopier;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author  likejei
 */
public class BeanMapperUtil {

    private static final ConcurrentHashMap<String, BeanCopier> BEAN_COPIER_MAP = new ConcurrentHashMap<>();

    public static void copyProperties(Object source, Object target) {
        String beankey = generateKey(source.getClass(), target.getClass());
        BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), false);
        BEAN_COPIER_MAP.putIfAbsent(beankey, copier);
        copier = BEAN_COPIER_MAP.get(beankey);
        copier.copy(source, target, null);
    }

    private static String generateKey(Class<?> class1, Class<?> class2) {
        return class1.toString() + class2.toString();
    }
}
