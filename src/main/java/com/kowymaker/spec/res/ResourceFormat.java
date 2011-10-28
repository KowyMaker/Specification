package com.kowymaker.spec.res;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import com.kowymaker.spec.utils.FileUtils;
import com.kowymaker.spec.utils.data.DataBuffer;
import com.kowymaker.spec.utils.data.DynamicDataBuffer;

public abstract class ResourceFormat<T extends ResourceFile>
{
    protected final byte[] signature;
    protected final byte   version;
    
    public ResourceFormat(byte version, byte... signature)
    {
        this.signature = signature;
        this.version = version;
    }
    
    //Getters, setters
    
    public byte[] getSignature()
    {
        return signature;
    }
    
    public byte getVersion()
    {
        return version;
    }
    
    //Utilities
    
    protected static byte[] sign(String signature)
    {
        byte[] b = signature.getBytes(Charset.forName("US-ASCII"));
        
        return b;
    }
    
    public T load(File file) throws IOException
    {
        byte[] data = FileUtils.load(file);
        
        return load(data);
    }
    
    public T load(InputStream in) throws IOException
    {
        byte[] data = FileUtils.load(in);
        
        return load(data);
    }
    
    public T load(byte[] data) throws IOException
    {
        DataBuffer buf = new DynamicDataBuffer();
        buf.setReadableBytes(data);
        
        //Test signature
        byte[] testSignature = buf.readBytes(3);
        if (!testSignature.equals(signature))
        {
            return null;
        }
        
        //Test version
        byte testVersion = buf.readByte();
        if (testVersion < version)
        {
            return null;
        }
        
        return load(buf);
    }
    
    public abstract T load(DataBuffer buf);
    
    public void save(T res, File file) throws IOException
    {
        save(res, new FileOutputStream(file));
    }
    
    public void save(T res, OutputStream out) throws IOException
    {
        DataBuffer buf = new DynamicDataBuffer();
        buf.writeBytes(signature);
        buf.writeByte(version);
        
        save(res, buf);
        
        FileUtils.save(out, buf.getWritedBytes());
    }
    
    public abstract void save(T res, DataBuffer buf);
    
}
