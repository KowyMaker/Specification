package com.kowymaker.spec.utils.res;

import com.kowymaker.spec.utils.data.DataBuffer;
import com.kowymaker.spec.utils.data.Encodable;

public class Dimension implements Encodable
{
    private int width;
    private int height;
    
    public Dimension()
    {
        this(0, 0);
    }
    
    public Dimension(int width, int height)
    {
        this.width = width;
        this.height = height;
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public void setWidth(int width)
    {
        this.width = width;
    }
    
    public int getHeight()
    {
        return height;
    }
    
    public void setHeight(int height)
    {
        this.height = height;
    }
    
    public void setDimension(int width, int height)
    {
        this.width = width;
        this.height = height;
    }
    
    public void encode(DataBuffer buf)
    {
        buf.writeInteger(width);
        buf.writeInteger(height);
    }
    
    public void decode(DataBuffer buf)
    {
        width = buf.readInt();
        height = buf.readInt();
    }
}