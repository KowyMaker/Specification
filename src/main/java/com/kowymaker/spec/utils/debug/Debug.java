/**
 * This file is part of Kowy Maker.
 *
 * Kowy Maker is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kowy Maker is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Kowy Maker.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.kowymaker.spec.utils.debug;

import java.util.Map;

import com.google.common.collect.Maps;

public class Debug
{
    private final static Map<String, Long>   values  = Maps.newLinkedHashMap();
    private final static Map<String, Locker> lockers = Maps.newLinkedHashMap();
    private final static Map<String, Object> datas   = Maps.newLinkedHashMap();
    
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
        
        final long value = values.get(name);
        final long diff = System.currentTimeMillis() - value;
        
        return diff;
    }
    
    public static long diffAndUpdate(String name)
    {
        if (!values.containsKey(name))
        {
            return 0;
        }
        
        final long diff = diff(name);
        final long current = values.get(name);
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
    
    public static void setData(String name, Object value)
    {
        datas.put(name, value);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T getData(String name)
    {
        return (T) datas.get(name);
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
        private int       counter = 0;
        private final int max;
        
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
