package com.kowymaker.spec.utils.data;

import java.util.Map;

import com.google.common.collect.Maps;

public class Flags
{
    private final byte[]            bytes;
    private final int               size;
    
    private final Map<String, Flag> flags     = Maps.newLinkedHashMap();
    
    private byte                    lastIndex = 1;
    private int                     num       = 0;
    
    public Flags(byte[] bytes, int size)
    {
        this(size);
        System.arraycopy(bytes, 0, this.bytes, 0,
                Math.min(bytes.length, this.bytes.length));
    }
    
    public Flags(int size)
    {
        int r = size % 8;
        int q = (size - r) / 8;
        
        if (r > 0)
        {
            q++;
        }
        
        bytes = new byte[q];
        this.size = size;
    }
    
    public Flag createFlag(String name)
    {
        Flag flag = null;
        
        if (!flags.containsKey(name) && num < bytes.length)
        {
            int n = num;
            byte index = createIndex();
            flag = new Flag(name, n, index);
            
            flags.put(name, flag);
        }
        
        return flag;
    }
    
    public void setFlag(String name, boolean checked)
    {
        setFlag(flags.get(name), checked);
    }
    
    public void setFlag(Flag flag, boolean checked)
    {
        if (flag != null)
        {
            int n = flag.num;
            byte index = flag.index;
            
            byte b = bytes[n];
            
            byte diff = index;
            
            if (!checked)
            {
                diff = (byte) ~diff;
            }
            
            b = (byte) (b | diff);
            
            bytes[n] = b;
        }
    }
    
    public boolean hasFlag(String name)
    {
        return hasFlag(flags.get(name));
    }
    
    public boolean hasFlag(Flag flag)
    {
        if (flag != null)
        {
            int n = flag.num;
            byte index = flag.index;
            
            byte b = bytes[n];
            
            return ((b & index) == index);
        }
        
        return false;
    }
    
    private byte createIndex()
    {
        byte index = lastIndex;
        
        lastIndex *= 2;
        
        if (lastIndex > 8)
        {
            lastIndex = 1;
            num++;
        }
        
        return index;
    }
    
    public byte[] getBytes()
    {
        return bytes;
    }
    
    public int getSize()
    {
        return size;
    }
    
    public Map<String, Flag> getFlags()
    {
        return flags;
    }
    
    public byte getLastIndex()
    {
        return lastIndex;
    }
    
    public int getNum()
    {
        return num;
    }
    
    public static class Flag
    {
        private final String name;
        private final int    num;
        private final byte   index;
        
        public Flag(String name, int num, byte index)
        {
            this.name = name;
            this.num = num;
            this.index = index;
        }
        
        public String getName()
        {
            return name;
        }
        
        public int getNum()
        {
            return num;
        }
        
        public byte getIndex()
        {
            return index;
        }
    }
}
