package com.kowymaker.spec.res;

import com.kowymaker.spec.utils.data.DataBuffer;

public abstract class GraphicsFormat<T extends GraphicsFile> extends
        ResourceFormat<T>
{
    
    public GraphicsFormat(byte version, byte[] signature)
    {
        super(version, signature);
    }
    
    @Override
    public T load(DataBuffer buf)
    {
        String handlerClassName = buf.readString();
        
        T res = loadData(buf);
        res.setHandlerClassName(handlerClassName);
        
        return res;
    }
    
    @Override
    protected void saveToBuffer(T res, DataBuffer buf)
    {
        buf.writeString(res.getHandlerClassName());
        
        saveData(res, buf);
    }
    
    protected abstract T loadData(DataBuffer buf);
    
    protected abstract void saveData(T res, DataBuffer buf);
    
}
