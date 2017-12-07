package com.jxkj.cloud.util;

import org.apache.commons.beanutils.converters.AbstractConverter;

public class EnumConverter extends AbstractConverter
{
    private final Class<?> enumClass;
    
    public EnumConverter(Class<?> enumClass)
    {
        this(enumClass, null);
    }
    
    public EnumConverter(Class<?> enumClass, Object defaultValue)
    {
        super(defaultValue);
        this.enumClass = enumClass;
    }
    
    protected Class<?> getDefaultType()
    {
        return this.enumClass;
    }
    
    @SuppressWarnings("unchecked")
    protected Object convertToType(@SuppressWarnings("rawtypes") Class type, Object value)
    {
        String str = value.toString().trim();
        return Enum.valueOf(type, str);
    }
    
    protected String convertToString(Object value)
    {
        return value.toString();
    }
}