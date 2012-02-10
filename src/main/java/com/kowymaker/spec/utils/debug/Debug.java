package com.kowymaker.spec.utils.debug;

import java.util.Map;

import com.google.common.collect.Maps;

public class Debug
{
    private final static Map<String, Long>   values  = Maps.newLinkedHashMap();
    private final static Map<String, Locker> lockers = Maps.newLinkedHashMap();
    
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
    
    public static long diffAndUpdate(String name)
    {
        if (!values.containsKey(name))
        {
            return 0;
        }
        
        long diff = diff(name);
        long current = values.get(name);
        values.put(name, current + diff);
        
        return diff;
    }
    
    public static Locker createLocker(String name, Locker.Type type)
    {
        Locker locker = null;
        
        switch (type)
        {
            case BOOLEAN:
                locker = new BooleanLocker();
                break;
            
            case COUNTER:
                locker = new CounterLocker(2);
        }
        
        return locker;
    }
    
    public static Locker createLocker(String name, Locker locker)
    {
        if (!lockers.containsKey(name))
        {
            lockers.put(name, locker);
        }
        
        return locker;
    }
    
    public static Locker getLocker(String name)
    {
        return lockers.get(name);
    }
    
    public static interface Locker
    {
        public boolean lock();
        
        public boolean unlock();
        
        public static enum Type
        {
            BOOLEAN, COUNTER;
        }
    }
    
    public static class BooleanLocker implements Locker
    {
        private boolean state = false;
        
        public boolean lock()
        {
            if (state)
            {
                return false;
            }
            else
            {
                state = true;
            }
            
            return true;
        }
        
        public boolean unlock()
        {
            if (state)
            {
                state = false;
            }
            else
            {
                return false;
            }
            
            return true;
        }
        
    }
    
    public static class CounterLocker implements Locker
    {
        private int counter = 0;
        private int max;
        
        public CounterLocker(int max)
        {
            this.max = max;
        }
        
        public boolean lock()
        {
            if (counter < max)
            {
                counter++;
                return true;
            }
            
            return false;
        }
        
        public boolean unlock()
        {
            if (counter > 0)
            {
                counter--;
                return true;
            }
            
            return false;
        }
        
    }
}
