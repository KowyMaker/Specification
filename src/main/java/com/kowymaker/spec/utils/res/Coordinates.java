package com.kowymaker.spec.utils.res;

import com.kowymaker.spec.utils.data.DataBuffer;
import com.kowymaker.spec.utils.data.Encodable;

public class Coordinates implements Encodable
{
    private double x;
    private double y;
    
    public Coordinates()
    {
        this(0, 0);
    }
    
    public Coordinates(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public double getX()
    {
        return x;
    }
    
    public double getY()
    {
        return y;
    }
    
    public void setX(double x)
    {
        this.x = x;
    }
    
    public void setY(double y)
    {
        this.y = y;
    }
    
    public void setLocation(Coordinates coord)
    {
        setLocation(coord.getX(), coord.getY());
    }
    
    public void setLocation(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public void encode(DataBuffer buf)
    {
        buf.writeDouble(x);
        buf.writeDouble(y);
    }
    
    public void decode(DataBuffer buf)
    {
        this.x = buf.readDouble();
        this.y = buf.readDouble();
    }
}