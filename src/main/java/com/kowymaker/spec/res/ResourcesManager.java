package com.kowymaker.spec.res;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.kowymaker.spec.res.impl.*;

@SuppressWarnings("unused")
public class ResourcesManager
{
    private final static Map<Class<? extends ResourceFormat<? extends ResourceFile>>, ResourceFormat<? extends ResourceFile>> formats  = new HashMap<Class<? extends ResourceFormat<? extends ResourceFile>>, ResourceFormat<? extends ResourceFile>>();
    
    static
    {
        try
        {
            register(TileFormat.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void register(
            Class<? extends ResourceFormat<? extends ResourceFile>>... classes)
            throws NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException
    {
        for (Class<? extends ResourceFormat<? extends ResourceFile>> clazz : classes)
        {
            register(clazz);
        }
    }
    
    public static <T extends ResourceFormat<? extends ResourceFile>> void register(
            Class<T> clazz) throws NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException
    {
        Constructor<T> constructor = clazz.getConstructor();
        T format = constructor.newInstance();
        formats.put(clazz, format);
    }
    
    @SuppressWarnings("unchecked")
    public static <V extends ResourceFile, T extends ResourceFormat<V>> T get(
            Class<T> clazz)
    {
        return (T) formats.get(clazz);
    }
}
