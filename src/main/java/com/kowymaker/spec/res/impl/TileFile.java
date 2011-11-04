package com.kowymaker.spec.res.impl;

import com.kowymaker.spec.res.GraphicFile;

public class TileFile extends GraphicFile
{
    private double originX;
    private double originY;
    private byte[] image;
    
    public double getOriginX()
    {
        return originX;
    }
    
    public void setOriginX(double originX)
    {
        this.originX = originX;
    }
    
    public double getOriginY()
    {
        return originY;
    }
    
    public void setOriginY(double originY)
    {
        this.originY = originY;
    }
    
    public byte[] getImage()
    {
        return image;
    }
    
    public void setImage(byte[] image)
    {
        this.image = image;
    }
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("TileFile [originX=");
        builder.append(originX);
        builder.append(", originY=");
        builder.append(originY);
        builder.append(", handlerClassName=");
        builder.append(handlerClassName);
        builder.append("]");
        return builder.toString();
    }
}
