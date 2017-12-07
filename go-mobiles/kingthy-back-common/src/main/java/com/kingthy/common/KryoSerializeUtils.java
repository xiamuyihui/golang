package com.kingthy.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.serializers.BeanSerializer;
import com.kingthy.page.Page;
import com.kingthy.page.PageT;
import org.apache.commons.codec.binary.Base64;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.CollectionSerializer;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.esotericsoftware.kryo.serializers.MapSerializer;

/**
 * kryo序列化优点：速度快，序列化后体积小
 *
 * KryoSerializeUtils
 * 
 * 潘勇 2017年3月2日 下午3:01:59
 * 
 * @version 1.0.0
 *
 */
public class KryoSerializeUtils
{
    /**
     * 序列化对象
     * 
     * @param obj
     * @return
     * @author 潘勇
     * @return String
     * @begin 2017-03-02 15:03
     * @since 1.0.0
     */
    public static <T extends Serializable> String serializationObject(T obj)
    {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.register(obj.getClass(), new JavaSerializer());
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeClassAndObject(output, obj);
        output.flush();
        output.close();
        
        byte[] b = baos.toByteArray();
        try
        {
            baos.flush();
            baos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return new String(new Base64().encode(b));
    }
    
    /**
     * 反序列化对象
     * 
     * @param obj
     * @param clazz
     * @return
     * @author 潘勇
     * @return T
     * @begin 2017-03-02 15:03
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T deserializationObject(String obj, Class<T> clazz)
    {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.register(clazz, new JavaSerializer());
        
        ByteArrayInputStream bais = new ByteArrayInputStream(new Base64().decode(obj));
        Input input = new Input(bais);
        return (T)kryo.readClassAndObject(input);
    }

    /**
     * 序列化List对象
     * 
     * @param obj
     * @param clazz
     * @return
     * @author 潘勇
     * @return String
     * @begin 2017-03-02 15:03
     * @since 1.0.0
     */
    public static <T extends Serializable> String serializationList(List<T> obj, Class<T> clazz)
    {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(true);
        
        CollectionSerializer serializer = new CollectionSerializer();
        serializer.setElementClass(clazz, new JavaSerializer());
        serializer.setElementsCanBeNull(false);
        
        kryo.register(clazz, new JavaSerializer());
        kryo.register(ArrayList.class, serializer);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeObject(output, obj);
        output.flush();
        output.close();
        
        byte[] b = baos.toByteArray();
        try
        {
            baos.flush();
            baos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return new String(new Base64().encode(b));
    }
    
    /**
     * 反序列化List对象
     * 
     * @param obj
     * @param clazz
     * @return
     * @author 潘勇
     * @return List<T>
     * @begin 2017-03-02 15:03
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> List<T> deserializationList(String obj, Class<T> clazz)
    {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(true);
        
        CollectionSerializer serializer = new CollectionSerializer();
        serializer.setElementClass(clazz, new JavaSerializer());
        serializer.setElementsCanBeNull(false);
        
        kryo.register(clazz, new JavaSerializer());
        kryo.register(ArrayList.class, serializer);
        
        ByteArrayInputStream bais = new ByteArrayInputStream(new Base64().decode(obj));
        Input input = new Input(bais);
        return (List<T>)kryo.readObject(input, ArrayList.class, serializer);
    }
    public static <T extends Serializable> PageT<T> deserializationPageT(String obj, Class<T> clazz)
    {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(true);

        CollectionSerializer serializer = new CollectionSerializer();
        serializer.setElementClass(clazz, new JavaSerializer());
        serializer.setElementsCanBeNull(false);


        kryo.register(clazz, new JavaSerializer());
        kryo.register(PageT.class, new JavaSerializer());
        kryo.register(ArrayList.class, serializer);


        ByteArrayInputStream bais = new ByteArrayInputStream(new Base64().decode(obj));
        Input input = new Input(bais);

        return (PageT<T>)kryo.readObject(input, PageT.class,serializer);
    }
    /**
     * 序列化Map对象
     * 
     * @param obj
     * @param clazz
     * @return
     * @author 潘勇
     * @return String
     * @begin 2017-03-02 15:03
     * @since 1.0.0
     */
    public static <T extends Serializable> String serializationMap(Map<String, T> obj, Class<T> clazz)
    {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(true);
        
        MapSerializer serializer = new MapSerializer();
        serializer.setKeyClass(String.class, new JavaSerializer());
        serializer.setKeysCanBeNull(false);
        serializer.setValueClass(clazz, new JavaSerializer());
        serializer.setValuesCanBeNull(true);
        
        kryo.register(clazz, new JavaSerializer());
        kryo.register(HashMap.class, serializer);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeObject(output, obj);
        output.flush();
        output.close();
        
        byte[] b = baos.toByteArray();
        try
        {
            baos.flush();
            baos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return new String(new Base64().encode(b));
    }
    
    /**
     * 反序列化Map对象
     * 
     * @param obj
     * @param clazz
     * @return
     * @author 潘勇
     * @return Map<String,T>
     * @begin 2017-03-02 15:04
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> Map<String, T> deserializationMap(String obj, Class<T> clazz)
    {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(true);
        
        MapSerializer serializer = new MapSerializer();
        serializer.setKeyClass(String.class, new JavaSerializer());
        serializer.setKeysCanBeNull(false);
        serializer.setValueClass(clazz, new JavaSerializer());
        serializer.setValuesCanBeNull(true);
        
        kryo.register(clazz, new JavaSerializer());
        kryo.register(HashMap.class, serializer);
        
        ByteArrayInputStream bais = new ByteArrayInputStream(new Base64().decode(obj));
        Input input = new Input(bais);
        return (Map<String, T>)kryo.readObject(input, HashMap.class, serializer);
    }
    
    /**
     * 序列化Set对象
     * 
     * @param obj
     * @param clazz
     * @return
     * @author 潘勇
     * @return String
     * @begin 2017-03-02 15:04
     * @since 1.0.0
     */
    public static <T extends Serializable> String serializationSet(Set<T> obj, Class<T> clazz)
    {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(true);
        
        CollectionSerializer serializer = new CollectionSerializer();
        serializer.setElementClass(clazz, new JavaSerializer());
        serializer.setElementsCanBeNull(false);
        
        kryo.register(clazz, new JavaSerializer());
        kryo.register(HashSet.class, serializer);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeObject(output, obj);
        output.flush();
        output.close();
        
        byte[] b = baos.toByteArray();
        try
        {
            baos.flush();
            baos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return new String(new Base64().encode(b));
    }
    
    /**
     * 反序列化Set对象
     * 
     * @param obj
     * @param clazz
     * @return
     * @author 潘勇
     * @return Set<T>
     * @begin 2017-03-02 15:04
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> Set<T> deserializationSet(String obj, Class<T> clazz)
    {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(true);
        
        CollectionSerializer serializer = new CollectionSerializer();
        serializer.setElementClass(clazz, new JavaSerializer());
        serializer.setElementsCanBeNull(false);
        
        kryo.register(clazz, new JavaSerializer());
        kryo.register(HashSet.class, serializer);
        
        ByteArrayInputStream bais = new ByteArrayInputStream(new Base64().decode(obj));
        Input input = new Input(bais);
        return (Set<T>)kryo.readObject(input, HashSet.class, serializer);
    }
    
    /**
     * 普通java序列化对象
     * 
     * @param object
     * @return
     * @author 潘勇
     * @return byte[]
     * @begin 2017-03-02 15:04
     * @since 1.0.0
     */
    public static byte[] serialize(Object object)
    {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try
        {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        }
        catch (Exception e)
        {
            
        }
        return null;
    }
    
    /**
     * 普通java反序列化对象
     * 
     * @param bytes
     * @return
     * @author 潘勇
     * @return Object
     * @begin 2017-03-02 15:04
     * @since 1.0.0
     */
    public static Object unserialize(byte[] bytes)
    {
        ByteArrayInputStream bais = null;
        try
        {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        }
        catch (Exception e)
        {
            
        }
        return null;
    }
}