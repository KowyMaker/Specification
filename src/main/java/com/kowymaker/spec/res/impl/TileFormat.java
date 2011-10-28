package com.kowymaker.spec.res.impl;

import com.kowymaker.spec.res.GraphicFormat;
import com.kowymaker.spec.utils.data.DataBuffer;

public class TileFormat extends GraphicFormat<TileFile>
{
    
    public TileFormat()
    {
        super((byte) 0x01, sign("kmt"));
    }
    
    @Override
    public TileFile load(DataBuffer buf)
    {
        TileFile file = new TileFile();
        
        file.setOriginX(buf.readDouble());
        file.setOriginY(buf.readDouble());
        file.setImage(buf.readBytes(buf.getReadableBytesSize()
                - buf.getReadPointer()));
        
        return file;
    }
    
    @Override
    public void save(TileFile res, DataBuffer buf)
    {
        buf.writeDouble(res.getOriginX());
        buf.writeDouble(res.getOriginY());
        buf.writeBytes(res.getImage());
    }
    
}
