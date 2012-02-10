package com.kowymaker.spec.utils.debug;

import java.util.Map;

import com.google.common.collect.Maps;

public class Debug
{
    private final static Map<String, Long> values = Maps.newLinkedHashMap();
    
    public static void update(String name)
    {
        values.put(name, System.currentTimeMillis());
    }
    
    public static long diff(String name)
    {
        if (!values.containsKey(name))
        {
            return 0;
        }
        
        long value = values.get(name);
        long diff = System.currentTimeMillis() - value;
        
        return diff;
    }
}
